import React from "react";
import CodeMirror from '@uiw/react-codemirror';

class TraversalFixed extends React.Component {
  constructor(props) {
    super(props);
    this.state = { 
      name: '',
      returned: ''
    };
  }

  setReturnedValue(val) {this.setState({returned:val});  }

  handleChange = (event) => {
    this.setState({ [event.target.name]: event.target.value });
  }
  
  handleSubmit = (event) => {
    fetch('http://localhost:9000/filesfixed', {
        method: 'POST',
        headers: { "Content-Type":"application/json" },
        body: JSON.stringify(this.state)
      })
      .then(res => res.json())
      .then(res => {this.setState({ returned:res.value })}
      );
    event.preventDefault();
}

 render() {
   return(
    <div className="traversal">
      <div class="container">
        <div class="row align-items-center my-5"> 
          <div class="col-lg-5">
            <h1 class="font-weight-light">File fetch&#8482;</h1>
            <p>
            <form onSubmit={this.handleSubmit}>
        <label>
          Enter file name:&nbsp;
          <textarea type="text" cols="40" rows="1" value={this.state.value} name="name" onChange={this.handleChange}></textarea>
        </label>
        <input type="submit" value="Preview" />
      </form>
            </p>  
          </div>    
        </div>
        <CodeMirror
      value={this.state.returned}
      height="700px"
      options={{
        mode: "txt",
        lineNumbers: false
      }}
    />
      </div>  
    </div>
  );
}
}

export default TraversalFixed;