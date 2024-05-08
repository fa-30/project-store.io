import axios from "axios";
import React, { createContext, useEffect, useState } from "react";
import { useContext } from "react";
import toast from "react-hot-toast";
import { tokenContext } from "./token";

export const counterContext = createContext();

export default function ProductProvider({ children }) {
  const [numOfCartItems, setNumOfCartItems] = useState(0);
  const [totalCartPrice, setTotalCartPrice] = useState(0);
  const [allProducts, setAllProducts] = useState([]);
  const [cartId, setCartId] = useState(null);
  const [userId, setUserID] = useState(null);
  const { token, getPayload } = useContext(tokenContext);
  console.log("data populated", userId + "," + token);
  console.log("this is all products here: ", allProducts);

  function addProductTCart(id) {
    const formData = new FormData();
    formData.append("id", id);
    formData.append("quantity", 1);
    console.log("here are form data: ", formData);
    axios
      .post(
        `http://localhost:8090/addToCart/${getPayload(token).id}`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      )
      .then((res) => {
        toast.success("Added to cart", {
          duration: 1500,
          position: "top-center",
        });
        getUserCart();
        return true;
      })
      .catch((err) => {
        toast.error(err.response.data.error);
        console.log(err.response.data.error);
        return false;
      });
  }

  async function getUserCart() {
    const id = getPayload(token || localStorage.getItem("token")).id;
    try {
      const res = await Promise.all([
        axios.get(`http://localhost:8090/shoppingCart`),
        axios.get(`http://localhost:8090/total_price/${id}`),
      ]);
      console.log("this is in get cart: ", res);
      setAllProducts(res[0].data);
      setTotalCartPrice(res[1].data);
      // setNumOfCartItems(res[1].data);
      // setCartId(res[0].data.id);
      setUserID(id);
    } catch (err) {
      console.log(err);
    }
  }
  // TODO: complete me here
  async function updateCount(id, newCount) {
    let booleanFlag = false;
    await axios
      .post(`http://localhost:8090/updateShoppingCart`, {
        productId: id,
        quantity: newCount,
      })
      .then((res) => {
        setTotalCartPrice(res.data.cart.totalCartPrice);
        setNumOfCartItems(res.data.cart.numOfCartItems);
        const { totalCartPrice, numberOfItems, ...rest } = res.data.items;
        setAllProducts(rest);
        booleanFlag = true;
      })
      .catch((err) => {
        booleanFlag = false;
      })
      .finally(() => getUserCart());

    return booleanFlag;
  }
  // TODO: modify and complete me
  async function deleteProduct(id) {
    let flag = false;
    try {
      const res = await axios.get(`http://localhost:8090/removeCartItem/${id}`);
      const { numberOfItems, totalPrice, items } = res.data.cart;
      console.log("rest is here: ", items, numberOfItems, totalPrice);
      setAllProducts(items);
      setNumOfCartItems(numberOfItems);
      setTotalCartPrice(totalPrice);
      flag = true;
    } catch (err) {
      console.log(err);
      flag = false;
    } finally {
      getUserCart();
      return flag;
    }
  }

  async function clearCart() {
    try {
      const res = await axios.get(
        `http://localhost:8090/clearShoppingCart/${getPayload(token).id}`
      );
      setAllProducts([]);
      setNumOfCartItems("");
      setTotalCartPrice(0);
      console.log("this is the response", res);
      return true; // Return true after successful deletion
    } catch (err) {
      console.error(err);
      if (err.response) {
        // Check for specific error codes or messages from the server
        console.error("Error status:", err.response.status);
        console.error("Error data:", err.response.data);
      }

      return false; // Return false on error
    } finally {
      getUserCart();
    }
  }

  useEffect(() => {
    (async () => {
      await getUserCart();
      console.log("i am hereeeee")
    })()
    if (localStorage.getItem("token")) {
      setUserID(getPayload(localStorage.getItem("token")).id);
    }
  }, []);

  return (
    <counterContext.Provider
      value={{
        numOfCartItems,
        totalCartPrice,
        allProducts,
        addProductTCart,
        getUserCart,
        updateCount,
        deleteProduct,
        clearCart,
        cartId,
      }}
    >
      {children}
    </counterContext.Provider>
  );
}
