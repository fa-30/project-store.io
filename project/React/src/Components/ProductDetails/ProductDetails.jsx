import React, { useContext } from 'react'
import { useQuery } from 'react-query';
import { Navigate, useNavigate, useParams } from 'react-router-dom'
import Loader from '../Loader/Loader';
import axios from 'axios';
import { counterContext } from '../../Context/product';
import ProductsSlider from '../ProductsSlider/ProductsSlider';

export default function ProductDetails() {

    const { addProductTCart } = useContext(counterContext)
    const { id } = useParams();

    function getProductDetails() {
        return axios.get(`http://localhost:8090/product/searchProducts/${id}`);
    }

    const getproductQuery = useQuery(`productDetails+${id}`, getProductDetails)

    if ( getproductQuery.isLoading) {
        return <Loader />
    }

    

    const productData =  getproductQuery.data.data;

    async function addProduct(id) {
        await addProductTCart(id);
    }

    return <>
        <div className="container">
            <div className="row align-items-center">
                <div className="col-md-3" >
                    <figure>
                        <img src={productData.imageName} alt={productData.name} className='w-100' style={{ height: "400px" }}/>
                    </figure>
                </div>
                <div className="col-md-9">
                    <figcaption>
                        <div>
                            <div>
                                <h2>{productData.name}</h2>
                                <p>{productData.longDescription}</p>
                            </div>
                            <div className='d-flex'>
                                <p><button className='btn btn-success mt-3 me-3'><i class="fa-solid fa-sack-dollar pe-1"></i>Price : {productData.price} LE </button></p>
                                <p><button className='btn btn-info mt-3 me-3'> <i class="fa-solid fa-list pe-1"></i>Category : {productData.category}</button></p>
                            </div>
                        </div>
                        <button className='btn btn-success w-100 mb-5' onClick={() => addProduct(productData.id)}>Add to cart <i class="fa-brands fa-shopify ps-1"></i></button>
                    </figcaption>
                </div>
            </div>
            <ProductsSlider />
        </div>
    </>
}
