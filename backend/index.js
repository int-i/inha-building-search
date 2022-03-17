import express from "express";
import cookieParser from "cookie-parser";
import api from "./routers/router";
import http from "http";
import connectDB from "./db";

const app = express();
const port = 8080;

connectDB();

app.use(express.urlencoded({
    extended: true
}));
app.use(express.json());
app.use(cookieParser());
app.use("/api", api)

app.get('/', (req, res) => {
    res.send('Hello world!');
});


http.createServer(app).listen(port, () => {
    console.log("서버 리스닝에 성공했습니다.");
});

