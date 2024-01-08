import GoogleApp from "./GoogleApp";
import NaverApp from "./NaverLoginFrontend";
import KakaoApp from "./KakaoApp";
import NaverLoginFrontend from "./NaverLoginFrontend";
import NaverLoginBackend from "./NaverLoginBackend";

const App = () => {
  return (
    <>
      <NaverLoginBackend />
      <NaverLoginFrontend />
    </>
  );
};

export default App;
