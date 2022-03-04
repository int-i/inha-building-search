import express from "express";
import User from "../models/User";
import mongoose from "mongoose";
const router = express.Router(); //라우터 객체 

// user 조회
router.get("/", (req, res) => {
    User.find({users: req.params.users}, {_id: 0, name: 1, phoneNum: 1, userID: 1, passward: 1}) // id 제외 이름과 번호만 보이도록 조정
        .then((users) => {
            res.json(users);
        })
        .catch((err) => {
            console.error(err);
            next(err);
        })
});

// user 추가
router.post("/", (req, res, next) => {
    const user = new User(
        // 이렇게 생긴거 싹다 json문법
     
        {
            name: req.body.name,
            phoneNum: req.body.phoneNum,
            userID: req.body.userID,
            passward: req.body.passward
        }
    )
    
    

    user.save()
        .then((result) => {
            res.json({result:"Success"});
        })

        .catch((err) => {
            console.error(err);
            res.json({result:"Fail"})    //postman을 통해 db에 값이 제대로 들어갔는지 확인하기 위해 성공 시, result:0, 에러시 result:1이 res되도록 설정
            next(err);
   
        })
});

// 비동기


router.post('/signup', async (req, res) => { 
    let new_user = new User (req.body); //json 데이터 자체를 지칭
    
    await new_user.save((err) => { 
        if(err) return res.status(500).json({message: "Failed..."});
        else return res.status(200).json({message: "Success!", data: new_user});

    });
});


router.post('/signin', async(req, res) => {
    let result = await User.findOne({ userID: req.body.userID, passward: req.body.passward})
    if(!result) return res.status(404).json({message: "No user!"}); //404: 접속 오류
    return res.status(200).json({message: "Found!", data: result});

    });


module.exports = router; //하나의 router 모듈로

//postman 으로 데이터 삽입 확인!