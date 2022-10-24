import logo from './logo.svg';
import './App.css';
import CorsConfirm from './components/practice/CorsConfirm';
import { Box } from '@mui/system';
import Prac001 from './components/practice/Prac001';
import Prac002 from './components/practice/Prac002';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Layout from './components/others/Layout';
import Main from './components/bodys/Main';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<CorsConfirm />} />
      </Routes>
    </BrowserRouter>
  );
  // return <Prac001 />;
  //return <CorsConfirm />;

  // return (
  //   <div>
  //     <CorsConfirm />
  //   </div>
  // );
}

export default App;
