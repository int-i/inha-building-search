import express from "express";
import api from "./routers/index";
import axios from "axios";
import http from "http";
import connectDB from "./db";

import bodyParser from "body-parser";

const app = express(); 

connectDB();

app.use(express.json()); //req의 body 정보를 읽을 수 있도록 설정, 모듈_json 해석기
app.use("/api", api); //api 연결



const httpPort = 8080;
http.createServer(app).listen(httpPort, () => {
    console.log("서버 리스닝에 성공했습니다.");
});
