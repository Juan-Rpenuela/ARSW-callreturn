# ARSW-callreturn

Colección de ejercicios sobre *Call & Return*, sockets TCP/UDP, HTTP básico y RMI.

## Requisitos previos
- Java (JDK 8+)
- Maven

Compilar todo el proyecto:
```
mvn clean package
```

---
## Ejercicio 1 – Analizando una URL
Archivo: `Ejercicio1/URLInfo.java`

Muestra las partes principales de una URL fija (`http://www.google.com:80/index.html`).

Ejecutar:
```
java -cp target/classes edu.escuelaing.arsw.app.Ejercicio1.URLInfo
```
Salida esperada (aprox.):
```
Protocolo: http
Host: www.google.com
Puerto: 80
Path: /index.html
File: /index.html
Query: null
Ref: null
```

---
## Ejercicio 2 – Mini “Browser” (descarga HTML)
Archivo: `Ejercicio2/Browser.java`

Lee por consola una URL, descarga su contenido y lo guarda como `resultado.html` en el directorio donde se ejecuta.

Ejecutar:
```
java -cp target/classes edu.escuelaing.arsw.app.Ejercicio2.Browser
```
Luego abrir `resultado.html` en un navegador.

---
## Ejercicio 3 – Socket TCP simple (cuadrado de números)
Archivos: `Ejercicio3/Server.java`, `Ejercicio3/Client.java`

El servidor (puerto 35000) recibe números y responde su cuadrado.

1. Iniciar servidor:
```
java -cp target/classes edu.escuelaing.arsw.app.Ejercicio3.Server
```
2. En otra terminal iniciar cliente:
```
java -cp target/classes edu.escuelaing.arsw.app.Ejercicio3.Client
```
3. Escribir números (ENTER). Ejemplo:
```
5
Respuesta: Respuesta: 25
```

---
## Ejercicio 4 – Socket TCP con selección de función trigonométrica
Archivos: `Ejercicio4/Server.java`, `Ejercicio4/Client.java`

El servidor mantiene un estado de función actual: `sen`, `cos` o `tan` (por defecto `sen`).
- Cambiar función: enviar `fun:sen`, `fun:cos` o `fun:tan`.
- Enviar un número para obtener `func(numero)`.

1. Servidor:
```
java -cp target/classes edu.escuelaing.arsw.app.Ejercicio4.Server
```
2. Cliente:
```
java -cp target/classes edu.escuelaing.arsw.app.Ejercicio4.Client
```
3. Ejemplo de interacción:
```
fun:cos
Función cambiada a cos
2
Respuesta:  -0.4161468365471424
```

---
## Ejercicio 5 – Servidor HTTP muy básico de archivos estáticos
Archivo: `Ejercicio5/HttpServer.java`
Recursos: `Ejercicio5/resources/index.html`, `poke.png`

Escucha en puerto 35000 y sirve archivos desde `WEB_ROOT`:
`src/main/java/edu/escuelaing/arsw/app/Ejercicio5/resources`

Rutas:
- `/` → `index.html`
- `/index.html` → `index.html`
- `/poke.png` → imagen PNG
- Cualquier otro archivo dentro del directorio si existe.

Ejecutar:
```
java -cp target/classes edu.escuelaing.arsw.app.Ejercicio5.HttpServer
```
Luego en navegador: `http://localhost:35000/`

---
## Ejercicio 6 – UDP Time Server / Client
Archivos: `Ejercicio6/DatagramServer.java`, `DatagramTimeClient.java`

Servidor UDP (puerto 4445) responde con la fecha/hora actual a cualquier datagrama recibido.
Cliente envía cada 5s el texto `TIME` y muestra:
- Hora nueva recibida
- Si hay timeout (3s) muestra la última hora mantenida.

1. Servidor:
```
java -cp target/classes edu.escuelaing.arsw.app.Ejercicio6.DatagramServer
```
2. Cliente:
```
java -cp target/classes edu.escuelaing.arsw.app.Ejercicio6.DatagramTimeClient
```

---
## Ejercicio 7 – Chat RMI sencillo
Archivos: `Ejercicio7/ChatPeer.java`, `ChatPeerImpl.java`, `ChatApp.java`

Chat P2P con RMI. Cada peer:
1. Pide nombre
2. Pide puerto local y publica objeto remoto `chatPeer`
3. Permite conectar a otro peer y enviar mensajes.

Ejecutar en dos terminales (puertos distintos):
```
java -cp target/classes edu.escuelaing.arsw.app.Ejercicio7.ChatApp
```
Luego en uno de los dos:
```
/connect 127.0.0.1 <puertoDelOtro>
```
Enviar mensajes escribiendo texto normal. Comandos:
- `/connect <host> <puerto>`
- `/exit`

---

## Estructura del proyecto
Cada ejercicio es independiente y demuestra un concepto de comunicación distinto.

---
## Autor
Juan Andrés Rodriguez Peñuela