import {
  Grid,
  Link,
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
import { getBoardData, getFirstBoardData } from './../../myfunc/requestFunc';

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

  // 데이터 가져오는거 해야함
  useEffect(() => {
    getFirstBoardData(setBoard);
  }, []);

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
                  <TableCell>{element.title}</TableCell>
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
