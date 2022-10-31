import axios from 'axios';
import { BASEURL } from '../assets/AllUrl';

export const getBoardData = async () => {
  // 이코드를 useEffect에서 쓸 수 없는 이유는 Promise를 반환하기 때문
  const data = await axios.get(BASEURL + 'board/all');
  return data; // 이렇게 써놔도 Promise 객체를 반환함
};

// 메인화면 갈때마다 게시판 데이터 모두 가져오기
// https://citronbanana.tistory.com/110
export const getFirstBoardData = async (setState) => {
  // then 사용하는 방법(현재 axios만 쓰는 이 코드에선 async await 없어도 동작됨)
  // await axios.get(BASEURL + 'board/all').then((res) => {
  //   setState(res.data);
  // });

  // then 사용하지 않는 방법
  const response = await axios.get(BASEURL + 'board/all');
  setState(response.data);
};

export const getFirstBoardData01 = async (setState, boardData) => {
  console.log('getFirstBoardData01 : ', boardData);
  if (boardData === null) {
    const response = await axios.get(BASEURL + 'board/all');
    setState(response.data);
    console.log('여기가 마지막인건 말이안됨');
  } else {
    console.log('여기 안들어와?', boardData);
    console.log('boardData.title :', boardData.title);
    console.log('boardData.id :', boardData.id);
    console.log('boardData.writer :', boardData.writer);
    // 여기서 알게된 사실 2개
    // null 과 '' 는 다른거임 null로 했을 때 계속 안됐었음
    // 지금 짜놓은 밑의 엑시오스 쪽이 요청하는 쿼리문에서 ''를 한개라도 보내면 모두 참으로 다 가져오는식으로 되어있음
    const response = await axios.get(BASEURL + 'board/all/search', {
      params: {
        id: boardData.id === '' ? 0 : boardData.id,
        writer: boardData.writer === '' ? '5호647jjk ㅓ09' : boardData.writer,
        title: boardData.title === '' ? '7ㅕ hty45' : boardData.title,
      },
    });
    setState(response.data);

    // 왜 생각대로 안나오는지 알았음(쿼리문 때문에(문자열 포함하는 식으로 만들어서) + id 는 ''값이면 아예 안됨 int라서)
    // const response = await axios.get(BASEURL + 'board/all/search', {
    //   params: {
    //     id: boardData.id === '' ? 0 : boardData.id,
    //     writer: boardData.writer,
    //     title: boardData.title,
    //   },
    // });
    // setState(response.data);
  }
};

// 상세보기 데이터 가져오기
export const getIdBoardData = async (boardId, setReadData) => {
  const readData = await axios.get(BASEURL + 'board/all/' + boardId);
  console.log('readData', readData);
  setReadData(readData.data);
};

// 로그인
export const loginGetJWT = async (userInfo, setLogin, navigate) => {
  await axios
    .post(BASEURL + 'myAuth/login', JSON.stringify(userInfo), {
      headers: {
        'Content-type': `application/json`,
      },
    })
    .then((res) => {
      console.log('jwt : ', res.data);
      setLogin(true);
      navigate('/main');
    })
    .catch((err) => {
      console.log('err : ', err);
      alert('아이디 비번이 틀렸습니다.');
    });
};
