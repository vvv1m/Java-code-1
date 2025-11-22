
-- 1. 科室表（Department）

CREATE TABLE department (
    dept_id VARCHAR(10) PRIMARY KEY COMMENT '科室编号',
    dept_name VARCHAR(50) NOT NULL UNIQUE COMMENT '科室名称',
    location VARCHAR(100) COMMENT '科室位置',
    phone VARCHAR(20) COMMENT '科室电话',
    description TEXT COMMENT '科室简介',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_dept_name (dept_name)
) ENGINE=InnoDB COMMENT='科室信息表';


-- 2. 医生表（Doctor）

CREATE TABLE doctor (
    doctor_id VARCHAR(20) PRIMARY KEY COMMENT '医生工号',
    doctor_name VARCHAR(50) NOT NULL COMMENT '医生姓名',
    gender ENUM('M', 'F') NOT NULL COMMENT '性别',
    birth_date DATE COMMENT '出生日期',
    phone VARCHAR(20) NOT NULL COMMENT '联系电话',
    email VARCHAR(100) COMMENT '电子邮箱',
    dept_id VARCHAR(10) NOT NULL COMMENT '所属科室',
    title ENUM('主任医师', '副主任医师', '主治医师', '住院医师') NOT NULL COMMENT '职称',
    specialty VARCHAR(200) COMMENT '专长',
    registration_fee DECIMAL(10,2) DEFAULT 0 COMMENT '挂号费',
    status ENUM('在职', '休假', '离职') DEFAULT '在职' COMMENT '状态',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (dept_id) REFERENCES department(dept_id) ON DELETE RESTRICT,
    INDEX idx_doctor_name (doctor_name),
    INDEX idx_dept_id (dept_id),
    INDEX idx_title (title)
) ENGINE=InnoDB COMMENT='医生信息表';


-- 3. 患者表（Patient）

CREATE TABLE patient (
    patient_id VARCHAR(20) PRIMARY KEY COMMENT '患者编号',
    patient_name VARCHAR(50) NOT NULL COMMENT '患者姓名',
    gender ENUM('M', 'F') NOT NULL COMMENT '性别',
    birth_date DATE NOT NULL COMMENT '出生日期',
    id_card VARCHAR(18) UNIQUE COMMENT '身份证号',
    phone VARCHAR(20) NOT NULL COMMENT '联系电话',
    address VARCHAR(200) COMMENT '家庭住址',
    emergency_contact VARCHAR(50) COMMENT '紧急联系人',
    emergency_phone VARCHAR(20) COMMENT '紧急联系电话',
    blood_type ENUM('A', 'B', 'AB', 'O', 'Unknown') DEFAULT 'Unknown' COMMENT '血型',
    allergy_history TEXT COMMENT '过敏史',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_patient_name (patient_name),
    INDEX idx_phone (phone),
    INDEX idx_id_card (id_card)
) ENGINE=InnoDB COMMENT='患者信息表';

-- 4. 医生排班表（Schedule）

CREATE TABLE doctor_schedule (
    schedule_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '排班ID',
    doctor_id VARCHAR(20) NOT NULL COMMENT '医生工号',
    work_date DATE NOT NULL COMMENT '工作日期',
    time_slot ENUM('上午', '下午', '晚上') NOT NULL COMMENT '时段',
    max_appointments INT DEFAULT 30 COMMENT '最大预约数',
    booked_count INT DEFAULT 0 COMMENT '已预约数',
    status ENUM('正常', '暂停', '取消') DEFAULT '正常' COMMENT '状态',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id) ON DELETE CASCADE,
    UNIQUE KEY uk_schedule (doctor_id, work_date, time_slot),
    INDEX idx_work_date (work_date),
    INDEX idx_doctor_date (doctor_id, work_date)
) ENGINE=InnoDB COMMENT='医生排班表';


-- 5. 预约挂号表（Appointment）

CREATE TABLE appointment (
    appointment_id VARCHAR(30) PRIMARY KEY COMMENT '预约编号',
    patient_id VARCHAR(20) NOT NULL COMMENT '患者编号',
    doctor_id VARCHAR(20) NOT NULL COMMENT '医生工号',
    schedule_id INT NOT NULL COMMENT '排班ID',
    appointment_date DATE NOT NULL COMMENT '预约日期',
    time_slot ENUM('上午', '下午', '晚上') NOT NULL COMMENT '时段',
    queue_number INT COMMENT '排队号',
    status ENUM('已预约', '已就诊', '已取消', '爽约') DEFAULT '已预约' COMMENT '状态',
    registration_fee DECIMAL(10,2) COMMENT '挂号费',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id) ON DELETE RESTRICT,
    FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id) ON DELETE RESTRICT,
    FOREIGN KEY (schedule_id) REFERENCES doctor_schedule(schedule_id) ON DELETE RESTRICT,
    INDEX idx_patient_id (patient_id),
    INDEX idx_doctor_id (doctor_id),
    INDEX idx_appointment_date (appointment_date),
    INDEX idx_status (status)
) ENGINE=InnoDB COMMENT='预约挂号表';


-- 6. 诊断记录表（Diagnosis）

CREATE TABLE diagnosis (
    diagnosis_id VARCHAR(30) PRIMARY KEY COMMENT '诊断编号',
    appointment_id VARCHAR(30) NOT NULL COMMENT '预约编号',
    patient_id VARCHAR(20) NOT NULL COMMENT '患者编号',
    doctor_id VARCHAR(20) NOT NULL COMMENT '医生工号',
    diagnosis_date DATETIME NOT NULL COMMENT '诊断时间',
    chief_complaint TEXT COMMENT '主诉',
    present_illness TEXT COMMENT '现病史',
    physical_exam TEXT COMMENT '体格检查',
    diagnosis_result TEXT NOT NULL COMMENT '诊断结果',
    treatment_plan TEXT COMMENT '治疗方案',
    doctor_advice TEXT COMMENT '医嘱',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (appointment_id) REFERENCES appointment(appointment_id) ON DELETE RESTRICT,
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id) ON DELETE RESTRICT,
    FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id) ON DELETE RESTRICT,
    INDEX idx_patient_id (patient_id),
    INDEX idx_doctor_id (doctor_id),
    INDEX idx_diagnosis_date (diagnosis_date)
) ENGINE=InnoDB COMMENT='诊断记录表';


-- 7. 药品表（Medicine）

CREATE TABLE medicine (
    medicine_id VARCHAR(20) PRIMARY KEY COMMENT '药品编号',
    medicine_name VARCHAR(100) NOT NULL COMMENT '药品名称',
    generic_name VARCHAR(100) COMMENT '通用名',
    dosage_form VARCHAR(50) COMMENT '剂型',
    specification VARCHAR(50) COMMENT '规格',
    manufacturer VARCHAR(100) COMMENT '生产厂家',
    unit_price DECIMAL(10,2) NOT NULL COMMENT '单价',
    stock_quantity INT DEFAULT 0 COMMENT '库存数量',
    min_stock INT DEFAULT 100 COMMENT '最低库存',
    expiry_date DATE COMMENT '有效期',
    category VARCHAR(50) COMMENT '药品分类',
    prescription_required BOOLEAN DEFAULT TRUE COMMENT '是否需要处方',
    description TEXT COMMENT '药品说明',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_medicine_name (medicine_name),
    INDEX idx_category (category),
    CHECK (stock_quantity >= 0)
) ENGINE=InnoDB COMMENT='药品信息表';


-- 8. 处方表（Prescription）

CREATE TABLE prescription (
    prescription_id VARCHAR(30) PRIMARY KEY COMMENT '处方编号',
    diagnosis_id VARCHAR(30) NOT NULL COMMENT '诊断编号',
    patient_id VARCHAR(20) NOT NULL COMMENT '患者编号',
    doctor_id VARCHAR(20) NOT NULL COMMENT '医生工号',
    prescription_date DATETIME NOT NULL COMMENT '开方时间',
    total_amount DECIMAL(10,2) DEFAULT 0 COMMENT '处方总金额',
    status ENUM('未付费', '已付费', '已取药', '已作废') DEFAULT '未付费' COMMENT '状态',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (diagnosis_id) REFERENCES diagnosis(diagnosis_id) ON DELETE RESTRICT,
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id) ON DELETE RESTRICT,
    FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id) ON DELETE RESTRICT,
    INDEX idx_patient_id (patient_id),
    INDEX idx_doctor_id (doctor_id),
    INDEX idx_status (status)
) ENGINE=InnoDB COMMENT='处方表';



-- 9. 处方明细表（Prescription_Detail）

CREATE TABLE prescription_detail (
    detail_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '明细ID',
    prescription_id VARCHAR(30) NOT NULL COMMENT '处方编号',
    medicine_id VARCHAR(20) NOT NULL COMMENT '药品编号',
    quantity INT NOT NULL COMMENT '数量',
    unit_price DECIMAL(10,2) NOT NULL COMMENT '单价',
    dosage VARCHAR(100) COMMENT '用法用量',
    frequency VARCHAR(50) COMMENT '用药频率',
    duration VARCHAR(50) COMMENT '用药时长',
    subtotal DECIMAL(10,2) AS (quantity * unit_price) STORED COMMENT '小计',
    FOREIGN KEY (prescription_id) REFERENCES prescription(prescription_id) ON DELETE CASCADE,
    FOREIGN KEY (medicine_id) REFERENCES medicine(medicine_id) ON DELETE RESTRICT,
    INDEX idx_prescription_id (prescription_id),
    INDEX idx_medicine_id (medicine_id),
    CHECK (quantity > 0)
) ENGINE=InnoDB COMMENT='处方明细表';



-- 10. 检查项目表（Examination）

CREATE TABLE examination (
    exam_id VARCHAR(20) PRIMARY KEY COMMENT '检查项目编号',
    exam_name VARCHAR(100) NOT NULL COMMENT '检查项目名称',
    category VARCHAR(50) COMMENT '检查类别',
    price DECIMAL(10,2) NOT NULL COMMENT '价格',
    description TEXT COMMENT '项目说明',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_exam_name (exam_name),
    INDEX idx_category (category)
) ENGINE=InnoDB COMMENT='检查项目表';


-- 11. 检查记录表（Examination_Record）

CREATE TABLE examination_record (
    record_id VARCHAR(30) PRIMARY KEY COMMENT '检查记录编号',
    diagnosis_id VARCHAR(30) NOT NULL COMMENT '诊断编号',
    patient_id VARCHAR(20) NOT NULL COMMENT '患者编号',
    exam_id VARCHAR(20) NOT NULL COMMENT '检查项目编号',
    exam_date DATETIME COMMENT '检查时间',
    result TEXT COMMENT '检查结果',
    conclusion TEXT COMMENT '检查结论',
    examiner VARCHAR(50) COMMENT '检查医生',
    status ENUM('待检查', '已完成', '已取消') DEFAULT '待检查' COMMENT '状态',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (diagnosis_id) REFERENCES diagnosis(diagnosis_id) ON DELETE RESTRICT,
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id) ON DELETE RESTRICT,
    FOREIGN KEY (exam_id) REFERENCES examination(exam_id) ON DELETE RESTRICT,
    INDEX idx_patient_id (patient_id),
    INDEX idx_exam_date (exam_date)
) ENGINE=InnoDB COMMENT='检查记录表';


-- 12. 收费记录表（Payment）

CREATE TABLE payment (
    payment_id VARCHAR(30) PRIMARY KEY COMMENT '收费编号',
    patient_id VARCHAR(20) NOT NULL COMMENT '患者编号',
    payment_type ENUM('挂号费', '诊疗费', '药品费', '检查费', '其他') NOT NULL COMMENT '收费类型',
    amount DECIMAL(10,2) NOT NULL COMMENT '金额',
    payment_method ENUM('现金', '支付宝', '微信', '银行卡', '医保') NOT NULL COMMENT '支付方式',
    payment_date DATETIME NOT NULL COMMENT '收费时间',
    cashier VARCHAR(50) COMMENT '收费员',
    related_id VARCHAR(30) COMMENT '关联单据号',
    invoice_no VARCHAR(30) COMMENT '发票号',
    status ENUM('已收费', '已退费') DEFAULT '已收费' COMMENT '状态',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id) ON DELETE RESTRICT,
    INDEX idx_patient_id (patient_id),
    INDEX idx_payment_date (payment_date),
    INDEX idx_payment_type (payment_type)
) ENGINE=InnoDB COMMENT='收费记录表';


-- 13. 用户表（User）- 系统用户

CREATE TABLE system_user (
    user_id VARCHAR(20) PRIMARY KEY COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password_hash VARCHAR(255) NOT NULL COMMENT '密码哈希',
    real_name VARCHAR(50) NOT NULL COMMENT '真实姓名',
    role ENUM('管理员', '医生', '护士', '药师', '收费员') NOT NULL COMMENT '角色',
    related_id VARCHAR(20) COMMENT '关联ID（如医生工号）',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '电话',
    status ENUM('启用', '禁用') DEFAULT '启用' COMMENT '状态',
    last_login DATETIME COMMENT '最后登录时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_role (role)
) ENGINE=InnoDB COMMENT='系统用户表';

-- 创建视图


-- 1. 医生工作量统计视图
DROP VIEW IF EXISTS v_doctor_workload;
CREATE VIEW v_doctor_workload AS
SELECT 
    d.doctor_id,
    d.doctor_name,
    dept.dept_name,
    d.title,
    COUNT(DISTINCT a.appointment_id) AS total_appointments,
    COUNT(DISTINCT CASE WHEN a.status = '已就诊' THEN a.appointment_id END) AS completed_appointments,
    COUNT(DISTINCT dia.diagnosis_id) AS total_diagnoses,
    COALESCE(SUM(p.total_amount), 0) AS total_prescription_amount
FROM 
    doctor d
LEFT JOIN 
    department dept ON d.dept_id = dept.dept_id
LEFT JOIN 
    appointment a ON d.doctor_id = a.doctor_id
LEFT JOIN 
    diagnosis dia ON d.doctor_id = dia.doctor_id
LEFT JOIN 
    prescription p ON dia.diagnosis_id = p.diagnosis_id
GROUP BY 
    d.doctor_id, d.doctor_name, dept.dept_name, d.title;

-- 2. 患者就诊历史视图
DROP VIEW IF EXISTS v_patient_visit_history;
CREATE VIEW v_patient_visit_history AS
SELECT 
    p.patient_id,
    p.patient_name,
    p.gender,
    TIMESTAMPDIFF(YEAR, p.birth_date, CURDATE()) AS age,
    a.appointment_id,
    a.appointment_date,
    a.time_slot,
    d.doctor_name,
    dept.dept_name,
    dia.diagnosis_result,
    a.status AS appointment_status
FROM 
    patient p
LEFT JOIN 
    appointment a ON p.patient_id = a.patient_id
LEFT JOIN 
    doctor d ON a.doctor_id = d.doctor_id
LEFT JOIN 
    department dept ON d.dept_id = dept.dept_id
LEFT JOIN 
    diagnosis dia ON a.appointment_id = dia.appointment_id;

-- 3. 药品库存预警视图
DROP VIEW IF EXISTS v_medicine_stock_alert;
CREATE VIEW v_medicine_stock_alert AS
SELECT 
    medicine_id,
    medicine_name,
    specification,
    stock_quantity,
    min_stock,
    (stock_quantity - min_stock) AS stock_diff,
    CASE 
        WHEN stock_quantity <= 0 THEN '缺货'
        WHEN stock_quantity <= min_stock THEN '库存不足'
        WHEN stock_quantity <= min_stock * 1.5 THEN '库存偏低'
        ELSE '库存正常'
    END AS stock_status,
    expiry_date,
    DATEDIFF(expiry_date, CURDATE()) AS days_to_expiry,
    CASE 
        WHEN DATEDIFF(expiry_date, CURDATE()) <= 30 THEN '即将过期'
        WHEN DATEDIFF(expiry_date, CURDATE()) <= 90 THEN '临近过期'
        ELSE '正常'
    END AS expiry_status
FROM 
    medicine
ORDER BY 
    stock_quantity ASC;

-- 4. 科室收入统计视图
DROP VIEW IF EXISTS v_department_revenue;
CREATE VIEW v_department_revenue AS
SELECT 
    dept.dept_id,
    dept.dept_name,
    DATE_FORMAT(pay.payment_date, '%Y-%m') AS month,
    COUNT(DISTINCT a.appointment_id) AS total_appointments,
    SUM(CASE WHEN pay.payment_type = '挂号费' THEN pay.amount ELSE 0 END) AS registration_revenue,
    SUM(CASE WHEN pay.payment_type = '药品费' THEN pay.amount ELSE 0 END) AS medicine_revenue,
    SUM(CASE WHEN pay.payment_type = '检查费' THEN pay.amount ELSE 0 END) AS examination_revenue,
    SUM(pay.amount) AS total_revenue
FROM 
    department dept
LEFT JOIN 
    doctor d ON dept.dept_id = d.dept_id
LEFT JOIN 
    appointment a ON d.doctor_id = a.doctor_id
LEFT JOIN 
    payment pay ON a.patient_id = pay.patient_id
WHERE 
    pay.status = '已收费'
GROUP BY 
    dept.dept_id, dept.dept_name, DATE_FORMAT(pay.payment_date, '%Y-%m');

-- 5. 每日预约统计视图
DROP VIEW IF EXISTS v_daily_appointment_stats;
CREATE VIEW v_daily_appointment_stats AS
SELECT 
    a.appointment_date,
    dept.dept_name,
    COUNT(*) AS total_appointments,
    SUM(CASE WHEN a.status = '已预约' THEN 1 ELSE 0 END) AS pending,
    SUM(CASE WHEN a.status = '已就诊' THEN 1 ELSE 0 END) AS completed,
    SUM(CASE WHEN a.status = '已取消' THEN 1 ELSE 0 END) AS cancelled,
    SUM(CASE WHEN a.status = '爽约' THEN 1 ELSE 0 END) AS no_show,
    ROUND(SUM(CASE WHEN a.status = '已就诊' THEN 1 ELSE 0 END) / COUNT(*) * 100, 2) AS completion_rate
FROM 
    appointment a
JOIN 
    doctor d ON a.doctor_id = d.doctor_id
JOIN 
    department dept ON d.dept_id = dept.dept_id
GROUP BY 
    a.appointment_date, dept.dept_name;


-- 创建额外的索引优化查询性能


-- 预约表的复合索引
CREATE INDEX idx_appointment_patient_date ON appointment(patient_id, appointment_date);
CREATE INDEX idx_appointment_doctor_date ON appointment(doctor_id, appointment_date);

-- 诊断表的复合索引
CREATE INDEX idx_diagnosis_patient_date ON diagnosis(patient_id, diagnosis_date);
CREATE INDEX idx_diagnosis_doctor_date ON diagnosis(doctor_id, diagnosis_date);

-- 处方表的复合索引
CREATE INDEX idx_prescription_patient_status ON prescription(patient_id, status);

-- 收费表的复合索引
CREATE INDEX idx_payment_patient_date ON payment(patient_id, payment_date);
CREATE INDEX idx_payment_date_type ON payment(payment_date, payment_type);

-- 药品表的库存索引
CREATE INDEX idx_medicine_stock ON medicine(stock_quantity);

-- 检查记录表的日期索引
CREATE INDEX idx_exam_record_date ON examination_record(exam_date);