import mongoose from "mongoose";

const { Schema } = mongoose; // 변수 선언처럼 보이지만 사실 mongoose에 있는 Schema 변수를 가져와 쓰겠다고 선언하는 것


const userSchema = new Schema(
    {
        // 필드 값들이 들어가는 위치
        userID : {type: String, required: true, trim: true},
        passward : {type: String, required: true, trim: true }, 
        name :{type: String, required: true, trim: true },
        department : {type: String, trim: true}
       
        // 암호화 -> string
    }, 
    {
        versionKey: false
    }
);

module.exports = mongoose.model("User", userSchema);

//mongoose 에 있는 schema로 변수 생성, mongoose.model로 model을 생성하여 export