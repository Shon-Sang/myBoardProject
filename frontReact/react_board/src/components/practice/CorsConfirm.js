import React, { useState } from 'react';
import axios from 'axios';
import { Box } from '@mui/system';
import { Button } from '@mui/material';
import { BASEURL } from '../../assets/AllUrl';

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

  const searchAllBoard = async () => {
    const data = await axios.get(BASEURL + 'board/all');
    console.log(data);
    return data.data;
  };

  const btn4Handle = async () => {
    const data = await searchAllBoard();
    console.log(data);
  }; // 중요 이런식으로 해야함

  const searchAllBoard02 = async () => {
    await axios.get(BASEURL + 'board/all').then((res) => {
      console.log(res.data);
    });
  };

  return (
    <>
      <Box>{boardData[0].username || ''}</Box>
      <Button onClick={hanldeMethod01}>버튼1</Button>
      <button onClick={searchData02}>버튼2</button>
      <button onClick={searchData03}>버튼3</button>
      <Button variant='outlined' onClick={btn4Handle}>
        보드데이터 버튼
      </Button>
    </>
  );
};

export default CorsConfirm;
