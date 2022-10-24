import axios from 'axios';
import { BASEURL } from '../assets/AllUrl';

export const getBoardData = async () => {
  const data = await axios.get(BASEURL + 'board/all');
  return data;
};

// 메인화면 갈때마다 게시판 데이터 모두 가져오기
// https://citronbanana.tistory.com/110
export const getFirstBoardData = (setState) => {
  axios.get(BASEURL + 'board/all').then((res) => {
    setState(res.data);
  });
};
