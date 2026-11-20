# Microservicio 2: Tienda (Productos y Pedidos) - LevelUp Gamer

Microservicio backend especializado en la **Gestión del Catálogo de Productos (CRUD completo), Inventario y Procesamiento de Órdenes del Carrito** para la plataforma **LevelUp Gamer** (Duoc UC - DSY1104 Evaluación Parcial 3).

---

## 🛠️ Tecnologías y Características
- **Puerto:** `8082`
- **Base de Datos:** MySQL (`levelup_tienda_db`)
- **Seguridad:** Spring Security 6 Stateless + Validación de JWT emitido por MS-Login (comparte misma firma criptográfica)
- **Documentación:** Swagger OpenAPI 3 (`http://localhost:8082/swagger-ui.html`)
- **CORS:** Habilitado para comunicación con React (`localhost:5173`)

---

## 📡 Endpoints del Microservicio

### 1. Catálogo de Productos (`/api/productos`)
| Método | Endpoint | Acceso | Descripción |
|---|---|---|---|
| `GET` | `/api/productos` | Público | Lista todos los productos (soporta filtros `?search=` y `?categoria=`). |
| `GET` | `/api/productos/{id}` | Público | Obtiene un producto por su código alfanumérico. |
| `POST` | `/api/productos` | `ROLE_ADMIN` | Crea un producto en el catálogo (valida DTO con `@Valid`). |
| `PUT` | `/api/productos/{id}` | `ROLE_ADMIN` | Modifica los datos de un producto existente. |
| `DELETE` | `/api/productos/{id}` | `ROLE_ADMIN` | Elimina un producto del catálogo por su ID. |

### 2. Pedidos y Carrito (`/api/pedidos`)
| Método | Endpoint | Acceso | Descripción |
|---|---|---|---|
| `POST` | `/api/pedidos` | Público/Auth | Procesa la compra del carrito, aplica cupón `DUOC20` (-20%) y registra la orden. |
| `GET` | `/api/pedidos` | Autenticado | Historial general de órdenes registradas. |
| `GET` | `/api/pedidos/{id}` | Autenticado | Desglose y detalle de un pedido específico. |

---

## 📦 Catálogo Inicial Preconfigurado (`DataInitializer`)
Al iniciar la aplicación por primera vez se cargan automáticamente los 9 productos oficiales del frontend (Catan, Carcassonne, PS5, Mando Xbox, Silla Gamer Titan, etc.).

---

## 🚀 Cómo Ejecutar

```powershell
cd "C:\Users\PC\Desktop\levelup-MSproductos\levelup-MSproductos"
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
.\mvnw.cmd spring-boot:run
```
Swagger UI disponible en: 👉 **[http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)**
