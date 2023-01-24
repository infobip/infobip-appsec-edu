import React from "react";
import DOMpurify from 'dompurify';

class XssFixed extends React.Component {
  constructor(props) {
    super(props);
    this.state = { 
      name: '',
      returned: ''
    };
    
    
  }

  setReturnedValue(val){this.setState({returned:val});  }

  handleChange = (event) => {
    this.setState({ [event.target.name]: event.target.value });
  }
  handleSubmit = (event) => {
    fetch('http://localhost:9000/xss', {
        method: 'POST',
        headers: { "Content-Type":"application/json" },
        body: JSON.stringify(this.state)
      })
      .then(res => res.json())
      .then(res => {
      var purified = DOMpurify.sanitize(res.value);   
      this.setState({ returned:purified })}
      );
    event.preventDefault();
}

 render() {
   return(
    <div className="xss">
      <div class="container">
        <div class="row align-items-center my-5"> 
          <div class="col-lg-5">
            <h1 class="font-weight-light">HTML Editor</h1>
            <p>
            <form onSubmit={this.handleSubmit}>
        <label>
          Enter HTML:&nbsp;
          <textarea type="text" cols="40" rows="5" value={this.state.value} name="name" onChange={this.handleChange}></textarea>
        </label>
        <input type="submit" value="Preview" />
       <div dangerouslySetInnerHTML = {{__html: this.state.returned}} />  
      </form>
            </p>
          </div>
        </div>
      </div>  
    </div>
  );
}
}

export default XssFixed;