import { request } from "./request";

export async function sendCode(data) {
  try {
    const response = await request({
      url: "/user/code",
      method: "post",
      data
    });
    return response;
  } catch (error) {
    console.error("Send code error:", error);
    throw error;
  }
}
