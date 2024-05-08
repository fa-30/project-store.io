import axios from "axios";
import React from "react";
import { useFormik } from "formik";
import * as yup from "yup";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { ColorRing } from "react-loader-spinner";
import { useQuery } from "react-query";
import Loader from "../Loader/Loader";
import toast from "react-hot-toast";

export default function AddProduct() {
  const [isSuccess, setIsSuccess] = useState(false);
  const [isFailed, setIsFailed] = useState(undefined);
  const [stillLoading, setStillLoading] = useState(false);
  let naviagte = useNavigate();

  function getCategories() {
    return axios.get("http://localhost:8090/Categorys");
  }

  const getCategoriesQuery = useQuery("getCategories", getCategories);

  async function addProduct(body) {
    setStillLoading(true);
    console.log("sent data values: ", body.category);
    console.log("sent data values: ", body);
    const { name, longDescription, price, imageName, quantity } = body;

    axios
      .post(`http://localhost:8090/product/add/${body.category}`, {
        name,
        longDescription,
        price,
        imageName,
        quantity,
      })
      .then(function (x) {
        setStillLoading(false);
        setIsSuccess(true);
        setTimeout(function () {
          setIsSuccess(false);
          naviagte("/AdminDashBoard");
        }, 2500);
      })
      .catch(function (x) {
        setStillLoading(false);
        console.log(x);
        setTimeout(function () {
          setIsFailed(undefined);
        }, 3000);
      });
  }

  const validateSchema = yup.object({
    name: yup.string().required("Title is required"),
    longDescription: yup.string().required("Description is required"),
    price: yup.number().required("Price is required"),
    quantity: yup.number().required("Quantity is required"),
    category: yup.string().required("Category is required"),
    imageName: yup.string().required("Image Cover is required"),
  });

  const registerForm = useFormik({
    initialValues: {
      name: "",
      longDescription: "",
      price: "",
      imageName: "",
      quantity: "",
    },

    validationSchema: validateSchema,
    onSubmit: addProduct,
  });

  if (getCategoriesQuery.isLoading) {
    return <Loader />;
  }

  // console.log(data.data.data);

  return (
    <>
      <div className="container mt-4 w-50">
        {isSuccess
          ? toast.success("Product added successfully", {
              duration: 2000,
              position: "top-center",
            })
          : null}
        {isFailed
          ? toast.error("Error occured", {
              duration: 2000,
              position: "top-center",
            })
          : null}

        <h3 className="mb-3">Add Product</h3>
        <form onSubmit={registerForm.handleSubmit}>
          <div className="mb-2">
            <label htmlFor="name" className="form-label">
              Title
            </label>
            <input
              type="text"
              className="form-control"
              id="name"
              value={registerForm.values.name}
              onChange={registerForm.handleChange}
              onBlur={registerForm.handleBlur}
            />
            {registerForm.errors.name && registerForm.touched.name ? (
              <div className="alert alert-danger mt-2">
                {registerForm.errors.name}
              </div>
            ) : null}
          </div>

          <div className="mb-2">
            <label htmlFor="longDescription" className="form-label">
              Description
            </label>
            <textarea
              className="form-control"
              id="longDescription"
              value={registerForm.values.longDescription}
              onChange={registerForm.handleChange}
              onBlur={registerForm.handleBlur}
              rows="3"
            ></textarea>
            {registerForm.errors.longDescription &&
            registerForm.touched.longDescription ? (
              <div className="alert alert-danger mt-2">
                {registerForm.errors.longDescription}
              </div>
            ) : null}
          </div>

          <div className="mb-2">
            <label htmlFor="category" className="form-label">
              Category
            </label>
            <select
              className="form-select"
              aria-label="Default select example"
              id="category"
              value={registerForm.values.category}
              onChange={registerForm.handleChange}
              onBlur={registerForm.handleBlur}
            >
              <option selected>Open this select menu</option>
              {getCategoriesQuery.data.data.map((category, idx) => (
                <option key={idx} value={category.name}>
                  {category.name}
                </option>
              ))}
            </select>
            {registerForm.errors.category && registerForm.touched.category ? (
              <div className="alert alert-danger mt-2">
                {registerForm.errors.category}
              </div>
            ) : null}
          </div>
          <div className="mb-2">
            <label htmlFor="price" className="form-label">
              Price
            </label>
            <input
              type="number"
              className="form-control"
              id="price"
              value={registerForm.values.price}
              onChange={registerForm.handleChange}
              onBlur={registerForm.handleBlur}
            />
            {registerForm.errors.price && registerForm.touched.price ? (
              <div className="alert alert-danger mt-2">
                {registerForm.errors.price}
              </div>
            ) : null}
          </div>
          <div className="mb-2">
            <label htmlFor="quantity" className="form-label">
              Quantity
            </label>
            <input
              type="number"
              className="form-control"
              id="quantity"
              value={registerForm.values.quantity}
              onChange={registerForm.handleChange}
              onBlur={registerForm.handleBlur}
            />
            {registerForm.errors.quantity && registerForm.touched.quantity ? (
              <div className="alert alert-danger mt-2">
                {registerForm.errors.quantity}
              </div>
            ) : null}
          </div>
          <div className="mb-2">
            <label htmlFor="imageName" className="form-label">
              Image Cover Link
            </label>
            <input
              type="text"
              className="form-control"
              id="imageName"
              value={registerForm.values.imageName}
              onChange={registerForm.handleChange}
              onBlur={registerForm.handleBlur}
            />
            {registerForm.errors.imageName && registerForm.touched.imageName ? (
              <div className="alert alert-danger mt-2">
                {registerForm.errors.imageName}
              </div>
            ) : null}
          </div>
          <div className="">
            <button
              className="btn bg-main mb-5 text-white d-block ms-auto"
              type="submit"
              disabled={!(registerForm.isValid && registerForm.dirty)}
            >
              {stillLoading ? (
                <ColorRing
                  visible={true}
                  height="40"
                  width="40"
                  ariaLabel="color-ring-loading"
                  wrapperStyle={{}}
                  wrapperclassName="color-ring-wrapper"
                  colors={["white", "white", "white", "white", "white"]}
                />
              ) : (
                "Add Product"
              )}
            </button>
          </div>
        </form>
      </div>
    </>
  );
}
