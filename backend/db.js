import mongoose, { connect } from "mongoose";
import { mongoURI } from "./server/config/key";

const connectDB = () => {
    mongoose.connect(mongoURI, {
        dbName: "test",
        ignoreUndefined: true,
        useNewUrlParser: true,
        useUnifiedTopology: true,
        useCreateIndex: true,
        useFindAndModify: false,
    }).then(() => {
        console.log("MongoDB Connected...");
    }).catch((err) => {
        console.log(err);
    })
};

mongoose.connection.on('error', (err) => {
    console.log('mongoDB Error occurred', err);
});

export default connectDB; // 다른 파일에서 해당 함수를 불러와서 쓸 수 있게끔 내보내기