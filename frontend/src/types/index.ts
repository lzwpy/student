export interface ApiResult<T> {
  code: number;
  msg: string;
  data: T;
}

export interface AuthLoginVO {
  token: string;
  teacherId: number;
  username: string;
  nickname: string;
}

export interface Classroom {
  id: number;
  teacherId: number;
  name: string;
}

export interface StudentPet {
  studentId: number;
  studentName: string;
  sortOrder: number;
  petId: number | null;
  petName: string | null;
  imageKey: string | null;
  level: number | null;
  exp: number | null;
  totalExp: number | null;
  coins: number | null;
}

export interface RuleItem {
  id: number;
  teacherId: number;
  name: string;
  type: "positive" | "negative";
  icon: string;
  expValue: number;
  coinValue: number;
  sortOrder: number;
}

export interface ShopItem {
  id: number;
  teacherId: number;
  name: string;
  description: string;
  image: string;
  price: number;
  stock: number;
  status: number;
}

export interface LeaderboardItem {
  studentId: number;
  studentName: string;
  petName: string;
  imageKey: string;
  level: number;
  totalExp: number;
  coins: number;
}
