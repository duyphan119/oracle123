# Đăng nhập: <POST>

url: /auth/login
body:{
email:"string",
password:"string"
}

# Đăng ký: <POST>

url: /auth/register
body:{
email:"string",
password:"string",
fullName:"string",
phone:"string"
}

# Cập nhật token: <POST>

url: /auth/refresh-token
body: {
refreshToken: "string" (optional)
}

# Đăng xuất: <POST>

url: /auth/logout

# Đổi mật khẩu: <PATCH> (token)

url: /auth/change-password
body:{
oldPassword:"string",
newPassword:"string"
}

# Xem thông tin tài khoản: <GET> (token)

url: /auth/my-profile

# Upload 1 hình

url: /upload/image/single
formData: {
image:File
}

# Lấy màu sắc, kích cỡ: <GET>

url: /variant

# Lấy tất cả category: <GET>

url: /category?parent_id=null
query example:{
parent_id:null
}

# Lấy tất cả sản phẩm: <GET>

url: /product
query example:{
alias: "nike-product"
}

# Lấy các sản phẩm theo danh mục: <GET>

url: /product/category
query example:{
alias:"nike",
min_price:1000000,
max_price:3000000,
variant_value_ids: 2,4,6
}

# Xem giỏ hàng của mình: <GET> (token)

url: /cart/account

# Thêm giỏ hàng: <POST> (token)

url: /cart-item
body: {
productDetailId: "string",
quantity: number
}

# Cập nhật số lượng trong giỏ hàng: <PATCH> (token)

url: /cart-item/{cartItemId}
body: {
productDetailId: "string",
quantity: number
}

# Xóa giỏ hàng: <DELETE> (token)

url: /cart-item/{cartItemId}

# Xem các đơn hàng của mình: <GET> (token)

url: /order/account

# Tạo đơn hàng: <POST> (token)

url: /order
body: {
province:"string",
district:"string",
ward:"string",
address: "string"
}

# Cập nhật trạng thái đơn hàng: <PATCH> (token: admin)

url: /order/{orderId}
body:{
status: "string" ["Đang xử lý", "Đang vận chuyển", "Vận chuyển thành công"]
}

# Xem danh sách yêu thích của mình: <GET> (token)

url: /wishlist/account

# Yêu thích sản phẩm: <POST> (token)

url: /wishlist
body:{
productId:"number"
}

# Bỏ yêu thích sản phẩm: <DELETE> (token)

url: /wishlist/{wishlistItemId}

# Xem doanh thu các tháng trong năm: <GET> (token: admin)

url: /statistics/revenue
query example:{
year: 2022
}

# Xem doanh thu các ngày trong tháng: <GET> (token: admin)

url: /statistics/revenue
query example:{
year: 2022,
month: 9
}

# Xem doanh thu hôm nay theo giờ: <GET> (token: admin)

url: /statistics/revenue
query example:{
year: 2022,
month: 9,
day: 23
}
