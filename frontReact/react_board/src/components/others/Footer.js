import { Button, Typography } from '@mui/material';
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { searchOptions } from '../../assets/MyDatas';

const Footer = () => {
  const navigate = useNavigate();

  return (
    <div>
      <Typography variant='h5'>푸터입니다.</Typography>
      <Button
        variant='outlined'
        onClick={() => {
          navigate('main', { state: { ...searchOptions, id: 3 } });
        }}
      >
        location 확인용
      </Button>
      <Button
        variant='outlined'
        onClick={() => {
          navigate('main');
        }}
      >
        location 확인용2
      </Button>

      <Button
        variant='outlined'
        onClick={() => {
          navigate('main', { state: { ...searchOptions, writer: 'd5' } });
        }}
      >
        location 확인용3
      </Button>
    </div>
  );
};

export default Footer;
