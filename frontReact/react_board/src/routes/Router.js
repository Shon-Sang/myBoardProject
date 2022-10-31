import React, { useState } from 'react';
import { Routes, Route, BrowserRouter } from 'react-router-dom';
import Login from '../components/bodys/Login';
import Main from '../components/bodys/Main';
import ReadContents from '../components/bodys/ReadContents';
import Layout from '../components/others/Layout';
// 여기서 useState board를 하나 만들어서 할 경우
// Main에 props로 board, setBoard 보내고 useEffect 로 처음 렌더링할 때만 데이터 받아오는 식으로 하기
// 다른 곳들 검색이나 다른 버튼 등등에서는
// setBoard를 위의 곳들에 보내서 엑시오스로 받아온 데이터를 set 해주기
// 근데 일단 useNavigate, useLocation 방법으로 했으니 이걸로 해보고 나중에 다시 만들 때 위방법으로
const Router = () => {
  const [login, setLogin] = useState(false);
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Layout login={login} setLogin={setLogin} />}>
          <Route path='main' element={<Main />} />
          <Route path='read/:boardId' element={<ReadContents />} />
          <Route path='login' element={<Login setLogin={setLogin} />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

export default Router;
