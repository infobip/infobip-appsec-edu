import React from 'react';

import ReactDOM from "react-dom";
import './index.css';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import {
  Navigation,
  Footer,
  Home,
  Xss,
  Xxe,
  XxeFixed,
} from "./components";
import XssFixed from './components/XssFixed';
import TemplateInjection from './components/TemplateInjection';
import Traversal from './components/Traversal';
import TraversalFixed from './components/TraversalFixed';


ReactDOM.render(
  <Router>
    <Navigation />
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/xss" element={<Xss />} />
      <Route path="/xssfixed" element={<XssFixed />}/>
      <Route path="/xxe" element={<Xxe />}/>
      <Route path="/xxefixed" element={<XxeFixed />}/>
      <Route path="/subscribe" element={<TemplateInjection />}/>
      <Route path="/files" element={<Traversal />}/>
      <Route path="/filesfixed" element={<TraversalFixed />}/>
    </Routes>
    <Footer />
  </Router>,

  document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
