import React from 'react';
import { Routes, Route, BrowserRouter } from 'react-router-dom';
import Main from '../components/bodys/Main';
import Layout from '../components/others/Layout';

const Router = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Layout />}>
          <Route path='main' element={<Main />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

export default Router;
