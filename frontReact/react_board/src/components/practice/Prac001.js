import React, { useState } from 'react';

const Prac001 = () => {
  const [data, setData] = useState({
    name: '',
    age: '',
    sex: '',
    email: '',
  });

  const inputData = (e) => {
    let key01 = e.target.name;
    setData({ ...data, [key01]: e.target.value });
  };

  return (
    <div>
      <input name='name' type='text' onChange={inputData} />
      <input name='age' type='text' onChange={inputData} />
      <input name='sex' type='text' onChange={inputData} />
      <input name='email' type='text' onChange={inputData} />
      <br />
      <p>{data.name}</p>
      <br />
      <p>{data.age}</p>
      <br />
      <p>{data.sex}</p>
      <br />
      <p>{data.email}</p>
    </div>
  );
};

export default Prac001;
