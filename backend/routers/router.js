import express from "express";
import users from "./users";


const router = express.Router();
router.get("/", (req, res) => {
    res.send( "index.js 파일입니다."); 
});

router.use("/users", users);


export default router;