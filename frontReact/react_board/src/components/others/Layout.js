import { Box, Divider } from '@mui/material';
import { Container } from '@mui/system';
import React from 'react';
import { Outlet } from 'react-router-dom';
import Footer from './Footer';
import Header from './Header';

const Layout = ({ login, setLogin }) => {
  return (
    <Box sx={{ display: 'flex', flexDirection: 'column' }}>
      {/* <Container maxWidth='sm'></Container> */}
      <Container fixed>
        <Header login={login} setLogin={setLogin} />
        <Divider />
        <Outlet />
        <Divider />
        <Footer />
      </Container>
    </Box>
  );
};

export default Layout;
