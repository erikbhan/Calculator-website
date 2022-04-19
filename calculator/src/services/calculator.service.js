import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "https://localhost:8443/api/";

class CalculatorService {
  async calculateExpression(a, b, op) {
    const response = await axios.post(
      API_URL + "calculation",
      {
        firstNumber: a,
        secondNumber: b,
        operator: op,
      },
      {
        headers: authHeader(),
      }
    );
    console.log(response.data.message);
    return response.data.message;
  }
}

export default new CalculatorService();
