import React, { useContext } from 'react'
import { useFormik } from 'formik'
import * as yup from 'yup'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { ColorRing } from 'react-loader-spinner';


export default function Register() {
  let naviagte = useNavigate();

  async function callRegister(req) {
    setIsLoading(true)
    console.log('register body:', req);
    axios.post(`http://localhost:8090/auth/register`, req)
      .then(function (x) {
        setIsLoading(false);
        setIsSuccess(true);
        setTimeout(function () {
          setIsSuccess(false)
          naviagte('/login');
        }, 2500);
      })
      .catch(function (x) {
        console.log("error occured", x)
        setIsLoading(false);
        // setIsFailed(x.response.message);
        // setIsFailed(x.response.data.message);
        setTimeout(function () {
          setIsFailed(undefined);
        }, 3000);
      })
  }


  const validateSchema = yup.object({
    "username": yup.string().required("username is required"),
    "firstName": yup.string().required("Name is required"),
    "lastName": yup.string().required("Name is required"),
    "email": yup.string().email('Email is not valid').required('Email is required'), 
    "password": yup.string().required('Password is required')
      .min(6, "Password must be at least 6 characters long")
      .max(32, "Password must be at most 32 characters long")
      .matches(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/, "Password format is invalid"),
    "password2": yup.string().required('Confirm password is required')
      .oneOf([yup.ref('password')], 'Passwords must match'),
  });


  const registerForm = useFormik({
    initialValues: {
      "username": "",
      "firstName": "",
      "lastName": "",
      "email": "",
      "password": "",
      "password2":""
    },

    validationSchema: validateSchema,
    onSubmit: callRegister
  })


  const [isSuccess, setIsSuccess] = useState(false);
  const [isFailed, setIsFailed] = useState(undefined);
  const [isLoading, setIsLoading] = useState(false);

  return <>

    <div className="container mt-4 w-50">

      {isSuccess ? <div className='alert alert-success text-center'>Account hase been created successfully</div> : null}
      {isFailed ? <div className='alert alert-danger text-center'>{isFailed}</div> : null}

      <h3 className='mb-3'>Register Now :</h3>
      <form onSubmit={registerForm.handleSubmit}>
        <div className="mb-2">
          <label htmlFor="userName" className="form-label">UserName :</label>
          <input type="text" className="form-control" id="username" value={registerForm.values.username} onChange={registerForm.handleChange} onBlur={registerForm.handleBlur} />
          {registerForm.errors.username && registerForm.touched.username ? <div className='alert alert-danger mt-2'>{registerForm.errors.username}</div> : null}
        </div>
        <div className="mb-2">
          <label htmlFor="firstName" className="form-label">First Name :</label>
          <input type="text" className="form-control" id="firstName" value={registerForm.values.firstName} onChange={registerForm.handleChange} onBlur={registerForm.handleBlur} />
          {registerForm.errors.firstName && registerForm.touched.firstName ? <div className='alert alert-danger mt-2'>{registerForm.errors.firstName}</div> : null}
        </div>
        <div className="mb-2">
          <label htmlFor="lastName" className="form-label">Last Name :</label>
          <input type="text" className="form-control" id="lastName" value={registerForm.values.lastName} onChange={registerForm.handleChange} onBlur={registerForm.handleBlur} />
          {registerForm.errors.lastName && registerForm.touched.lastName ? <div className='alert alert-danger mt-2'>{registerForm.errors.lastName}</div> : null}
        </div>
        <div className="mb-2">
          <label htmlFor="email" className="form-label">Email :</label>
          <input type="email" className="form-control" id="email" value={registerForm.values.email} onChange={registerForm.handleChange} onBlur={registerForm.handleBlur} />
          {registerForm.errors.email && registerForm.touched.email ? <div className='alert alert-danger mt-2'>{registerForm.errors.email}</div> : null}

        </div>
        <div className="mb-2">
          <label htmlFor="password" className="form-label">Password :</label>
          <input type="password" className="form-control" id="password" value={registerForm.values.password} onChange={registerForm.handleChange} onBlur={registerForm.handleBlur} />
          {registerForm.errors.password && registerForm.touched.password ? <div className='alert alert-danger mt-2'>{registerForm.errors.password}</div> : null}

        </div>
        <div className="mb-2">
          <label htmlFor="password2" className="form-label">Confirm password :</label>
          <input type="password" className="form-control" id="password2" value={registerForm.values.password2} onChange={registerForm.handleChange} onBlur={registerForm.handleBlur} />
          {registerForm.errors.password2 && registerForm.touched.password2 ? <div className='alert alert-danger mt-2'>{registerForm.errors.password2}</div> : null}

        </div>

        <div className=''>
          <button className='btn bg-main mb-5 text-white d-block ms-auto' type='submit'>
            {isLoading ? <ColorRing
              visible={true}
              height="40"
              width="40"
              ariaLabel="color-ring-loading"
              wrapperStyle={{}}
              wrapperClass="color-ring-wrapper"
              colors={['white', 'white', 'white', 'white', 'white']}
            /> : "Register"}
          </button>
        </div>
      </form>

    </div>
  </>
}