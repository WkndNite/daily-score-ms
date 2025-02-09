import { request } from "./request";

export async function register(data) {
  try {
    const response = await request({
      url: "/user/register",
      method: "post",
      data,
    });
    return response;
  } catch (error) {
    console.error("Registration error:", error);
    throw error;
  }
}
