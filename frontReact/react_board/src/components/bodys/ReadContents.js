import React, { useEffect, useState } from 'react';
import { Box, Divider, Typography } from '@mui/material';
import { useParams } from 'react-router-dom';
import { getIdBoardData } from '../../myfunc/requestFunc';

const ReadContents = () => {
  const { boardId } = useParams();
  const [readData, setReadData] = useState({});

  useEffect(() => {
    getIdBoardData(boardId, setReadData);
  }, []);

  return (
    <Box>
      <Typography>{readData.title}</Typography>
      <Divider />
      <Typography>{readData.readCount}</Typography>
      <Divider />
      <Typography>{readData.writer}</Typography>
      <Divider />
      <Typography>{readData.creationDate}</Typography>
      <Divider />
      <Typography>{readData.content}</Typography>
    </Box>
  );
};

export default ReadContents;
