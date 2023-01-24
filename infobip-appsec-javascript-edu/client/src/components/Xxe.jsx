import React, {useState} from 'react';
import axios from 'axios';
import CodeMirror from '@uiw/react-codemirror';


function Xxe() {

  const [file, setFile] = useState()
  const [text, setText] = useState()

  function handleChange(event) {
    setFile(event.target.files[0])
  }
  
  function handleSubmit(event) {
    event.preventDefault()
    const url = 'http://localhost:9000/xxe';
    const formData = new FormData();
    formData.append('file', file);
    formData.append('fileName', file.name);
    const config = {
     headers: {
      'content-type': 'multipart/form-data',
     },
    };
    axios.post(url, formData, config)
    .then((response) => {
      console.log(response.data);
      setText(response.data);
    });
  }

  return (
    <div className="xxe">
      <div class="container">
        <div class="row align-items-center my-5"> 
          <div class="col-lg-10">
        <form onSubmit={handleSubmit}>
          <h1>Upload your XML's</h1>
          <br></br>
          <input type="file" onChange={handleChange}/>
          <button type="submit">Upload</button>
        </form>
        <br></br>
        <br></br>
        Output:
        <CodeMirror
      value={text}
      height="700px"
      options={{
        mode: "xml",
        lineNumbers: false
      }}
    />
       </div>
      </div>
    </div>
  </div>  
    
  );
}

export default Xxe;