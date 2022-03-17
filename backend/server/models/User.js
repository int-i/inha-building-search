import { Schema, model } from "mongoose";
import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";

const saltRounds = 10;

const userSchema = new Schema(
    {
        name: {
            type: String,
            maxlength: 50,
        },
        email: {
            type: String,
            // space bar를 없애준다.
            trim: true,
            // 중복을 허용하지 않는다.
            unique: 1
        },
        password: {
            type: String,
            minlength: 5,
        }
    },
    {
        versionKey: false
    }
);

userSchema.pre("save", function (next) {
    const user = this;
    if (user.isModified("password")) {
        // 비밀번호 암호화
        bcrypt.genSalt(saltRounds, (err, salt) => {
            if (err) return next(err);
            bcrypt.hash(user.password, salt, (err, hash) => {
                if (err) return next(err);
                user.password = hash;
                next();
            });
        });
    }
    else next();
});

userSchema.methods.comparePassword = function (plainPassword, cb) {
    bcrypt.compare(plainPassword, this.password, (err, isMatch) => {
        if (err) return cb(err);
        cb(null, isMatch);
    });
}

userSchema.methods.generateToken = function (cb) {
    const user = this;
    const token = jwt.sign(user._id.toHexString(), 'secretToken');
    user.token = token;
    user.save((err, user) => {
        if (err) return cb(err);
        cb(null, user);
    });
}

userSchema.statics.findByToken = function (token, cb) {
    const user = this;
    jwt.verify(token, 'secretToken', (err, decoded) => {
        user.findOne({ "_id": decoded, "token": token }, (err, user) => {
            if (err) return cb(err);
            cb(null, user);
        });
    });
}


// 다른 파일에서도 이 모델을 쓸 수 있도록
export default model("User", userSchema);