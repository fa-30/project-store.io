import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import axios from 'axios';
import { useQuery } from 'react-query';
import Loader from '../Loader/Loader';


export default function Categories() {

  function getCategories() {
    return axios.get('http://localhost:8090/Categorys')
  }

  const getCategoriesQuery = useQuery('getCategories', getCategories)

  if (getCategoriesQuery.isLoading) {
    return <Loader />
  }

  return <>
    <div className="container">
      <div className="row text-center">
        {getCategoriesQuery.data.data.map((category, idx) => <div key={idx} className="col-md-4">
          <div className='mb-3 product'>
            <Card sx={{ maxWidth: 500 }}>
              <CardMedia
                sx={{ height: 250 }}
                image={category.imageName}
              />
              <CardContent>
                <Typography gutterBottom variant="h5" component="div" className='text-main'>
                  {category.name}
                </Typography>
              </CardContent>
            </Card>
          </div>
        </div>)}

      </div>
    </div>

    {/* <Pagination count={10} shape="rounded" /> */}
  </>
}