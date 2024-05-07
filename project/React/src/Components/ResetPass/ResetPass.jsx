import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { useFormik } from 'formik'
import * as yup from 'yup'
import toast from 'react-hot-toast';
import { useNavigate } from 'react-router-dom';
import { ColorRing } from 'react-loader-spinner';


export default function ResetPass() {

    const nav = useNavigate();
    const [isLoading, setIsLoading] = useState(false);
    const [isSuccess, setIsSuccess] = useState(false);

    const validateSchema = yup.object({
        "email": yup.string().email('email is not valid').required('email is required'),
    })

    const registerForm = useFormik({
        initialValues: {
            "email": "",
            "password": "",
            "password2": ""
        },

        validationSchema: validateSchema,
        onSubmit: ResetPass
    })

    function ResetPass() {
        const { email } = registerForm.values;
        const { password } = registerForm.values;
        const { password2 } = registerForm.values;
        setIsLoading(true)
        axios.put('http://localhost:8090/auth/change', {
            "email": email,
            "Password": password,
            "Password2": password2
        }).then((res) => {
            console.log('success',res);
            toast.success("Password successfully changed")
            setIsLoading(false);
            setIsSuccess(true);
            setTimeout(function () {
                nav('/home');
            }, 2500);
        }).catch((err) => {
            toast.error("Error occured")
        })
    }


    return <>
        <div className="container">
            <h2 className='mt-4 fw-bold'>Reset Your Password</h2>
            <form onSubmit={registerForm.handleSubmit}>
                <div className="col-md-12 col-sm-4">
                    <input
                        type="text"
                        className="form-control mt-3"
                        id="email"
                        placeholder="Email.."
                        value={registerForm.values.email}
                        onChange={registerForm.handleChange}
                        onBlur={registerForm.handleBlur}
                    />
                    {registerForm.errors.email && registerForm.touched.email ? <div className='alert alert-danger mt-2'>{registerForm.errors.email}</div> : null}
                    <input
                        type="password"
                        className="form-control mt-3"
                        id="password"
                        placeholder="Password.."
                        value={registerForm.values.password}
                        onChange={registerForm.handleChange}
                        onBlur={registerForm.handleBlur}
                    />
                    {registerForm.errors.password && registerForm.touched.password ? <div className='alert alert-danger mt-2'>{registerForm.errors.password}</div> : null}
                    <input
                        type="password"
                        className="form-control mt-3"
                        id="password2"
                        placeholder="Password.."
                        value={registerForm.values.password2}
                        onChange={registerForm.handleChange}
                        onBlur={registerForm.handleBlur}
                    />
                    <button className='btn btn-success mt-3' type='submit' disabled={!registerForm.values.password || !registerForm.values.password ||  registerForm.isSubmitting}>
                        {isLoading ? <ColorRing
                            visible={true}
                            height="40"
                            width="40"
                            ariaLabel="color-ring-loading"
                            wrapperStyle={{}}
                            wrapperClass="color-ring-wrapper"
                            colors={['white', 'white', 'white', 'white', 'white']}
                        /> : "Verify"}
                    </button>
                </div>
            </form>
        </div>
    </>
}