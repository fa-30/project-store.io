import React, { useState } from "react";
import Loader from "../Loader/Loader";
import axios from "axios";
import { useQuery } from "react-query";
import toast from "react-hot-toast";
import Lottie from "lottie-react";
import img from "../Cart/Animation - 1709313300851.json";
import { Link } from "react-router-dom";

export default function AdminDashBoard() {
  const [allProducts, setAllProducts] = useState(null);
  const [allCategories, setAllCategories] = useState(null);

  function getProducts() {
    return axios.get("http://localhost:8090/product/Products");
  }

  function getCategories() {
    return axios.get("http://localhost:8090/Categorys");
  }

  const getproductQuery = useQuery("getProducts", getProducts);
  const getCategoriesQuery = useQuery("getCategories", getCategories);
//   console.log("this products:", data);
//   console.log("this categories:", getCategoriesQuery);

  function deleteProduct(id) {
    axios
      .delete(`http://localhost:8090/product/Productsdelet/${id}`)
      .then((res) => {
        console.log(res);
        toast.success("Product Deleted Successfully", {
          position: "top-center",
        });
        setAllProducts(res);
      })
      .catch((err) => {
        toast.error("Error occuured", { position: "top-center" });
      });
  }

  function deleteCategory(id) {
    axios
      .delete(`http://localhost:8090/Categorysdelet/${id}`)
      .then((res) => {
        toast.success("Category Deleted Successfully", {
          position: "top-center",
        });
        setAllCategories(res);
      })
      .catch((err) => {
        toast.error("Error occuured", { position: "top-center" });
      });
  }

  // console.log(getCategoriesQuery.data.data.data);

  if (getproductQuery.isLoading || getCategoriesQuery.isLoading) {
    return <Loader />;
  }

  return (
    <>
      <div className="container">
        <div className="text-center mt-3">
          <h2 className="mb-3">Admin Page</h2>
          <button className="btn btn-primary me-3 p-3">
            Number of products : {getproductQuery?.data?.data.length}
          </button>
          <Link aria-current="page" to={"/AddProduct"}>
            <button className="btn btn-success me-3 p-3">
              Add New Product
            </button>
          </Link>
          <button className="btn btn-primary me-3 p-3">
            Number of categories : {getCategoriesQuery?.data?.data.length}
          </button>
          <Link aria-current="page" to={"/AddCategory"}>
            <button className="btn btn-success p-3">Add New Category</button>
          </Link>
          <div className="row">
            <div className="col-md-6">
              <h2 className="mt-4">Products</h2>
              {getproductQuery !== 0 ? (
                <div className="container w-75 mt-5">
                  {getproductQuery.data.data.map((product, idx) => (
                    <div className="row" key={idx}>
                      <div className="col-md-2 mb-1">
                        <img
                          src={product.imageName}
                          alt={product.name}
                          className="w-100"
                        />
                      </div>
                      <div className="col-md-6">
                        <h5>{product.name}</h5>
                      </div>
                      <div className="col-md-2">
                        <button
                          className="btn btn-outline-danger"
                          onClick={() => {
                            deleteProduct(product.id);
                          }}
                        >
                          Remove <i className="fa-regular fa-trash-can"></i>
                        </button>
                      </div>
                      <hr className="mt-2" />
                    </div>
                  ))}
                </div>
              ) : (
                <div className="d-flex flex-column align-items-center">
                  <div className="col-md-5 col-12">
                    <div className="text-center">
                      <Lottie animationData={img}></Lottie>
                    </div>
                  </div>
                </div>
              )}
            </div>
            <div className="col-md-6">
              <h2 className="mt-4">Categories</h2>
              {getCategoriesQuery !== 0 ? (
                <div className="container w-75 mt-5">
                  {getCategoriesQuery.data.data.map((category, idx) => (
                    <div className="row" key={idx}>
                      <div className="col-md-2 mb-1">
                        <img
                          src={category.imageName}
                          alt={category.name}
                          className="w-100"
                        />
                      </div>
                      <div className="col-md-6">
                        <h5>{category.name}</h5>
                      </div>
                      <div className="col-md-2">
                        <button
                          className="btn btn-outline-danger"
                          onClick={() => deleteCategory(category.categoryId)}
                        >
                          Remove <i className="fa-regular fa-trash-can"></i>
                        </button>
                      </div>
                      <hr className="mt-2" />
                    </div>
                  ))}
                </div>
              ) : (
                <div className="d-flex flex-column align-items-center">
                  <div className="col-md-5 col-12">
                    <div className="text-center">
                      <Lottie animationData={img}></Lottie>
                    </div>
                  </div>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
