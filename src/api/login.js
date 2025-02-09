import { request } from "./request";

export async function loginAccount(data) {
  try {
    const response = await request({
      url: "/user/loginAccount",
      method: "post",
      data,
    });
    return response;
  } catch (error) {
    console.error("Login error:", error);
    throw error;
  }
}
