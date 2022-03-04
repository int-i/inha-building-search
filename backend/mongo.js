import mongoose from "mongoose"; //mongoose 모듈 가져오기

const mongoDBURL = "mongodb://127.0.0.1:27017"; // mongoDB안의 test용 db 연결

const connectDB = () => {
    mongoose
        .connect(mongoDBURL , { 
            dbName: "Signup",
            ignoreUndefined: true,
            useNewUrlParser: true,
            useUnifiedTopology: true
        })
        .then(() => console.log("mongoDB connected")) //.then: 정상적으로 진행 시, 실행 (Promise)
        .catch((err) => console.log(err)); //,catch: 연결이 안될 때, 실행
};

mongoose.connection.on('error', (err) => {
    console.log('mongoDB Error occurred', err);
});

export default connectDB;