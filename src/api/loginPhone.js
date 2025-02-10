import { request } from "./request";

export async function loginPhone(data) {
  try {
    const response = await request({
      url: "/user/loginPhone",
      method: "post",
      data,
    });
    return response;
  } catch (error) {
    console.error("Login error:", error);
    throw error;
  }
}