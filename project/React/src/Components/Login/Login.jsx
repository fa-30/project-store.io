import React, { useContext } from 'react'
import { useFormik } from 'formik'
import * as yup from 'yup'
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { ColorRing } from 'react-loader-spinner';
import { tokenContext } from '../../Context/token';
import style from './Login.module.css'
import { jwtDecode } from 'jwt-decode';



export default function Login() {
  const [isFailed, setIsFailed] = useState(undefined);
  const [isLoading, setIsLoading] = useState(false);
  const [isSuccess, setIsSuccess] = useState(false);
  const { setToken } = useContext(tokenContext);
  let naviagte = useNavigate();

  async function callRegister(req) {
    setIsLoading(true)
    axios.post(`http://localhost:8090/auth/login`, req)
    .then(function (res) {
      setIsSuccess(true)
      setIsLoading(false);
      console.log(jwtDecode(res.data.jwt));
      console.log('this jwt here:', res.data.jwt)
      localStorage.setItem("token", res.data.jwt)
      setToken(res.data.jwt);
        setTimeout(function () {
            naviagte('/home');
        }, 1000);
      })
      .catch(function (err) {
        setIsLoading(false);
        console.log(err);
        // setIsFailed(err.response);
        setTimeout(function () {
          setIsFailed(undefined);
        }, 3000);
      })
  }

const validationSchema = yup.object({
    "email": yup.string().email('Email is not valid').required('Email is required'),
    "password": yup.string().required('Password is required')
      .min(6, "Password must be at least 6 characters long")
      .max(32, "Password must be at most 32 characters long")
  });

  const loginForm = useFormik({
    initialValues: {
      "email": "",
      "password": ""
    },

    validationSchema,
    onSubmit: callRegister
  })




  return <>

    <div className="container mt-4 w-50">

      {isSuccess ? <div className='alert alert-success text-center'>Logged in successfully</div> : null}
      {isFailed ? <div className='alert alert-danger text-center'>{isFailed}</div> : null}

      <h3 className='mb-3'>Login Now :</h3>
      <form onSubmit={loginForm.handleSubmit}>
        <div className="mb-2">
          <label htmlFor="email" className="form-label">email :</label>
          <input type="email" className="form-control" id="email" value={loginForm.values.email} onChange={loginForm.handleChange} onBlur={loginForm.handleBlur} />
          {loginForm.errors.email && loginForm.touched.email ? <div className='alert alert-danger mt-2'>{loginForm.errors.email}</div> : null}


        </div>
        <div className="mb-2">
          <label htmlFor="password" className="form-label">password :</label>
          <input type="password" className="form-control" id="password" value={loginForm.values.password} onChange={loginForm.handleChange} onBlur={loginForm.handleBlur} />
          {loginForm.errors.password && loginForm.touched.password ? <div className='alert alert-danger mt-2'>{loginForm.errors.password}</div> : null}


        </div>
        <div className='d-flex justify-content-between'>
          <button className='btn bg-main mb-5 text-white' type='submit'>
            {isLoading ? <ColorRing
              visible={true}
              height="40"
              width="40"
              ariaLabel="color-ring-loading"
              wrapperStyle={{}}
              wrapperClass="color-ring-wrapper"
              colors={['white', 'white', 'white', 'white', 'white']}
            /> : "Login"}
          </button>
          <Link to={'/forgetpass'}>
            <h5 className={style.editLink}>forget your password ?</h5>
          </Link>
        </div>
      </form>

    </div>
  </>
}