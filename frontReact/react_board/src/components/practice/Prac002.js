import React from 'react';

const ArrComp = (props) => {
  return (
    <>
      <tr>
        <td>{props.arrData.name}</td>
        <td>{props.arrData.age}</td>
      </tr>
    </>
  );
};

const ArrComp02 = ({ arrData }) => {
  return (
    <>
      <tr>
        <td>{arrData.name}</td>
        <td>{arrData.age}</td>
      </tr>
    </>
  );
};

const Prac002 = () => {
  const arr = [
    { name: 'aa', age: '22' },
    { name: 'bb', age: '11' },
    { name: 'cc', age: '33' },
    { name: 'dd', age: '23' },
  ];

  return (
    <div>
      <table>
        <thead>
          <tr>
            <th>이름</th>
            <th>나이</th>
          </tr>
        </thead>

        <tbody>
          {/* {arr.map((data) => {
            return (
              <>
                <tr>
                  <td>{data.name}</td>
                  <td>{data.age}</td>
                </tr>
              </>
            );
          })} */}

          {arr.map((data) => (
            <ArrComp arrData={data} key={data} />
          ))}

          {/* {arr.map((data) => (
            <ArrComp02 arrData={data} />
          ))} */}
        </tbody>
      </table>
    </div>
  );
};

export default Prac002;
