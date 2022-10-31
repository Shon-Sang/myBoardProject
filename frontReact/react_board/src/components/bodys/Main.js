import {
  Grid,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from '@mui/material';
import React, { useEffect, useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import {
  getBoardData,
  getFirstBoardData,
  getFirstBoardData01,
} from './../../myfunc/requestFunc';

//https://github.com/mui/material-ui/blob/v5.9.1/docs/data/material/getting-started/templates/dashboard/Orders.js
const mData = (num, tit, writer, dat, cnt) => {
  return {
    id: num,
    title: tit,
    writer: writer,
    creationDate: dat,
    readCount: cnt,
  };
};

const Main = () => {
  const [board, setBoard] = useState([]);
  const location = useLocation();

  // 데이터 가져오는거 해야함
  useEffect(() => {
    console.log('location 값 : ', location.state);
    getFirstBoardData01(setBoard, location.state);

    return () => {
      console.log('Main 컴포넌트 종료');
      // location.state = null;
    };
  }, [location]);

  // 이방법 말고 다른 방법으로 해보기
  // useEffect(() => {
  //   if (location.state === null) {
  //     console.log('여기들어감');
  //     console.log(location);
  //     getFirstBoardData(setBoard);
  //   } else {
  //     console.log(location.state.id);
  //     setBoard([]);
  //     location.state = null; // *중요* 이거 안해주면 if문 안걸림, const인데 이렇게 바꿀 수 있음
  //     console.log(location.state);
  //   }

  //   return () => {
  //     console.log('Main 컴포넌트 종료');
  //   };
  // }, [location]);

  // const data = board;
  //   for (let i = 0; i < 7; i++) {
  //     data = [...data, mData(1, 'h', 't', '22/08/08', 5)];
  //   }
  console.log(board);

  return (
    <>
      <Typography variant='h7' color='blueviolet'>
        게시판 글
      </Typography>
      <TableContainer>
        <Table size='medium'>
          <TableHead>
            <TableRow>
              <TableCell>글 번호</TableCell>
              <TableCell>제목</TableCell>
              <TableCell>글쓴이</TableCell>
              <TableCell>날짜</TableCell>
              <TableCell>조회수</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {board.map((element, idx) => {
              return (
                <TableRow key={idx}>
                  <TableCell>{element.id}</TableCell>
                  <TableCell>
                    {/* {'read/' + element.id} 밑에 방법은 백틱 */}
                    <Link to={`/read/${element.id}`} underline='none'>
                      {element.title}
                    </Link>
                  </TableCell>
                  <TableCell>{element.writer}</TableCell>
                  <TableCell>{element.creationDate}</TableCell>
                  <TableCell>{element.readCount}</TableCell>
                </TableRow>
              );
            })}
          </TableBody>
        </Table>
      </TableContainer>
    </>
  );
};

export default Main;
