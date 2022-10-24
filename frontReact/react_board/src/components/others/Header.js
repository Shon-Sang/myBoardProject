import { Box, Button, Typography, Link } from '@mui/material';
import React, { useState } from 'react';

const Header = () => {
  const [login, setLogin] = useState(false);

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
              setLogin(true);
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
      <Link href='/main' underline='none'>
        <Typography variant='h3'>게시판입니다</Typography>
      </Link>
    </Box>
  );
};

export default Header;
