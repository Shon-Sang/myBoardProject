import React, { useState } from 'react';
import axios from 'axios';
import { Box } from '@mui/system';
import { Button } from '@mui/material';

const CorsConfirm = () => {
  const [boardData, setBoardData] = useState([
    {
      address: '',
      age: 0,
      authRole: '',
      email: '',
      joinDate: '',
      name: '',
      refreshToken: '',
      username: '',
    },
  ]);

  const searchData01 = async () => {
    await axios
      .get('http://localhost:8090/all/searchAllUser')
      .then((response) => {
        console.log(response.data);
        setBoardData(response.data);
      });
  };

  const searchData02 = async () => {
    await axios
      .post('http://localhost:8090/myAuth/login', {
        username: 'hgd1',
        password: 'a1234',
      })
      .then((response) => {
        console.log(response.data);
      });
  };

  const searchData03 = async () => {
    await axios
      .get('http://localhost:8090/user/prac02', {
        headers: {
          Authorization:
            'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJoZ2QxIiwiYXV0aFJvbGUiOiJST0xFX01FTUJFUiIsImV4cCI6MTY2MzE4NzMxOSwidXNlcm5hbWUiOiJoZ2QxIn0.oCO-9HY3oaRZNV4u1Km1SkoGER7KoTRlWfvYeksPWBcTIhbgjJniCv6QjBQTDYL0OkLgCQpgq33dforaPD39Mw',
        },
      })
      .then((response) => {
        console.log(response.data);
      });
  };

  const hanldeMethod01 = () => {
    searchData01();
  };

  return (
    <>
      <Box>{boardData[0].username || ''}</Box>
      <Button onClick={hanldeMethod01}>버튼1</Button>
      <button onClick={searchData02}>버튼2</button>
      <button onClick={searchData03}>버튼3</button>
    </>
  );
};

export default CorsConfirm;
