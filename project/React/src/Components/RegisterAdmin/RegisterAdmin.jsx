import React, { useContext } from "react";
import { useFormik } from "formik";
import * as yup from "yup";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { ColorRing } from "react-loader-spinner";

export default function Register() {
  const [isSuccess, setIsSuccess] = useState(false);
  const [isFailed, setIsFailed] = useState(undefined);
  const [isLoading, setIsLoading] = useState(false);
  let naviagte = useNavigate();

  async function callRegister(req) {
    setIsLoading(true);
    console.log("body sent here:", req);
    axios
      .post(`http://localhost:8090/auth/registeradmin`, req)
      .then(function (res) {
        console.log(res);
        setIsLoading(false);
        setIsSuccess(true);
        console.log("i have passed");
        setTimeout(function () {
          setIsSuccess(false);
          naviagte("/loginAdmin");
        }, 2500);
      })
      .catch(function (err) {
        setIsLoading(false);
        console.log("I'm dead here", err.message);
        setIsFailed(err.message);
        setTimeout(function () {
          setIsFailed(undefined);
        }, 3000);
      });
  }

  const validateSchema = yup.object({
    email: yup
      .string()
      .email("email is not valid")
      .required("email is required"),
    password: yup.string().required("password is required"),
  });

  const registerForm = useFormik({
    initialValues: {
      email: "",
      password: "",
    },

    validationSchema: validateSchema,
    onSubmit: callRegister,
  });

  return (
    <div className="container mt-4 w-50">
      {isSuccess ? (
        <div className="alert alert-success text-center">
          Account hase been created successfully
        </div>
      ) : null}
      {isFailed ? (
        <div className="alert alert-danger text-center">{isFailed}</div>
      ) : null}

      <h3 className="mb-3">Let's add new admin :</h3>
      <form onSubmit={registerForm.handleSubmit}>
        <div className="mb-2">
          <label htmlFor="email" className="form-label">
            email :
          </label>
          <input
            type="email"
            className="form-control"
            id="email"
            value={registerForm.values.email}
            onChange={registerForm.handleChange}
            onBlur={registerForm.handleBlur}
          />
          {registerForm.errors.email && registerForm.touched.email ? (
            <div className="alert alert-danger mt-2">
              {registerForm.errors.email}
            </div>
          ) : null}
        </div>
        <div className="mb-2">
          <label htmlFor="password" className="form-label">
            password :
          </label>
          <input
            type="password"
            className="form-control"
            id="password"
            value={registerForm.values.password}
            onChange={registerForm.handleChange}
            onBlur={registerForm.handleBlur}
          />
          {registerForm.errors.password && registerForm.touched.password ? (
            <div className="alert alert-danger mt-2">
              {registerForm.errors.password}
            </div>
          ) : null}
        </div>
        <div className="">
          <button
            className="btn bg-main mb-5 text-white d-block ms-auto"
            type="submit"
          >
            {isLoading ? (
              <ColorRing
                visible={true}
                height="40"
                width="40"
                ariaLabel="color-ring-loading"
                wrapperClass="color-ring-wrapper"
                colors={["white", "white", "white", "white", "white"]}
              />
            ) : (
              "Register"
            )}
          </button>
        </div>
      </form>
    </div>
  );
}
