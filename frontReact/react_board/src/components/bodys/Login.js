import {
  Box,
  Button,
  Checkbox,
  FormControlLabel,
  Grid,
  Link,
  TextField,
} from '@mui/material';
import { Container } from '@mui/system';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { loginGetJWT } from '../../myfunc/requestFunc';

const Login = ({ setLogin }) => {
  const navigate = useNavigate();
  // const [userId, setUserId] = useState('');
  // const [userPW, setUserPW] = useState('');

  const [userInfo, setUserInfo] = useState({ username: '', password: '' });
  console.log('userInfo', userInfo);
  return (
    <Container maxWidth='xs'>
      <Box
        sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}
      >
        <TextField
          margin='normal'
          required
          fullWidth
          label='Email Address'
          name='email'
          autoFocus
          value={userInfo.username}
          onChange={(e) => {
            setUserInfo((prev) => {
              return { ...prev, username: e.target.value };
            });
          }}
        />
        <TextField
          margin='normal'
          required
          fullWidth
          name='password'
          label='Password'
          type='password'
          value={userInfo.password}
          onChange={(e) => {
            setUserInfo((prev) => {
              return { ...prev, password: e.target.value };
            });
          }}
        />

        <FormControlLabel
          control={<Checkbox value='remember' color='primary' />}
          label='Remember me'
        />
        <Button
          fullWidth
          variant='contained'
          sx={{ mt: 3, mb: 2 }}
          onClick={() => {
            loginGetJWT(userInfo, setLogin, navigate);
            // setLogin(true);
            // navigate('/main');
          }}
        >
          Sign In
        </Button>
        <Grid container>
          <Grid item='xs'>
            <Link>Forgot password?</Link>
          </Grid>

          <Grid item='xs'>
            <Link>Don't have an account? Sign Up</Link>
          </Grid>
        </Grid>
      </Box>
    </Container>
  );
};

export default Login;
