import { Box, Button, Typography, Link } from '@mui/material';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Header = ({ login, setLogin }) => {
  const navigate = useNavigate();
  return (
    <Box
      sx={{
        display: 'flex',
        flexDirection: 'column',
      }}
    >
      <Box sx={{ display: 'flex', justifyContent: 'flex-end' }}>
        {!login && (
          <Button
            variant='contained'
            onClick={() => {
              navigate('login');
            }}
          >
            로그인
          </Button>
        )}
        {login && (
          <Button
            variant='contained'
            onClick={() => {
              setLogin(false);
            }}
          >
            로그아웃
          </Button>
        )}
      </Box>
      {/* <Link href='/main' underline='none'> */}
      <Button
        onClick={() => {
          navigate('main');
        }}
        size='small'
      >
        <Typography variant='h3'>게시판입니다</Typography>
      </Button>

      {/* </Link> */}
    </Box>
  );
};

export default Header;
