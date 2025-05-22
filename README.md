# 🧰 Mold Preparation Time Estimator

A Java desktop application that estimates **three key production times** for custom molds based on user-provided parameters. The tool is designed for manufacturing and industrial contexts where quick and consistent time estimates are crucial.

### 🔍 Calculates:

- **Machine Preparation Time**
- **Machining Time**
- **Total Gluing Time**

---

## 🧠 How It Works

The estimations are driven by 7 user inputs:

1. **Length (cm)**
2. **Width (cm)**
3. **Height (cm)**
4. **Number of pieces**
5. **Total mold thickness (cm)**
6. **Maximum machining depth (cm)**
7. **Number of glue layers**

The logic follows these rules:

- **Gluing Time**:
  - 1 layer → 0 min
  - Each additional layer → +15 min

- **Preparation and Machining Times**:
  - Dynamically retrieved from an Excel file (`reglas_tiempos_produccion.xlsx`)
  - Rules define time ranges based on dimensions and other properties

Apache POI is used for reading and parsing Excel rules.

---

## 🖥️ User Interface

Built with **Java Swing**, the UI is simple but functional:

- Clean and organized layout for inputs
- Results shown in a styled output area
- Company logo loaded from `src/main/resources/img/logo.png`

> ⚠️ The GUI is still in progress — additional styling and features (e.g. input validation, file export) are planned.

---

## 📁 Project Structure

```
src/
└── main/
    ├── java/
    │   └── com/programa_costos/
    │       ├── MainApp.java                 # Application entry point
    │       ├── model/
    │       │   └── Molde.java               # Mold data structure
    │       ├── service/
    │       │   └── CalculadoraTiempos.java  # Core logic + Excel rule loading
    │       └── view/
    │           └── VentanaPrincipal.java    # GUI built with Swing
    └── resources/
        ├── img/
        │   └── logo.png                     # Logo displayed in the UI
        └── reglas_tiempos_produccion.xlsx   # Excel file with time rules
```

---

## 🚀 How to Run

1. **Clone the repository**:

```bash
git clone https://github.com/samuel-un/programa-calculo-tiempo-mecanizados.git
cd programa-calculo-tiempo-mecanizados
```

2. **Open in Eclipse, IntelliJ, NetBeans**, or any Java IDE

3. **Ensure dependencies are resolved** via Maven:
   - Apache POI libraries are required for Excel parsing

4. **Run the `MainApp.java` class**

---

## 🔧 Technologies Used

- Java 8+
- Maven for dependency management
- Swing (UI)
- Apache POI (Excel parsing)

---

## 📌 Current Limitations

- No persistent storage or export functionality yet
- Excel file must be placed in `/src/main/resources/`

---

## 📋 License

Licensed under the MIT License — free to use and modify.