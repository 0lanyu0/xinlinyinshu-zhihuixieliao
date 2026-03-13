CREATE TABLE Department
	(Deptname CHAR(20) PRIMARY KEY,
	Deptaddress CHAR(20) NOT NULL,
	Depttel CHAR(11) NOT NULL,
	COLUMN Deptcategory CHAR(20);
	);

CREATE TABLE Doctor
	(Dno CHAR(10) PRIMARY KEY,
	Dpassword VARCHAR(15) NOT NULL,
	Dname CHAR(20) NOT NULL,
	Dsex CHAR(2) NOT NULL,
	Dtitle CHAR(10) NOT NULL,
	Dage SMALLINT NOT NULL,
	Dtel CHAR(11) NOT NULL,
	Deptname CHAR(20) NOT NULL,
	FOREIGN KEY(Deptname) REFERENCES Department(Deptname)
	);

CREATE TABLE Ward
	(Wno CHAR(9) PRIMARY KEY,
	Deptname CHAR(20) NOT NULL,
	Wcharge INT NOT NULL,
	FOREIGN KEY(Deptname) REFERENCES Department(Deptname)
	);

CREATE TABLE Bed
	(Wno CHAR(9),
	Bno CHAR(9),
	Bstate CHAR(20) ,
	PRIMARY KEY(Wno,Bno),
	FOREIGN KEY(Wno) REFERENCES Ward(Wno)
	);

CREATE TABLE Patient
	(Pno CHAR(10) PRIMARY KEY,
	Pname CHAR(20) NOT NULL,
	Psex CHAR(2) NOT NULL,
	Pdiagnose CHAR(20) NOT NULL,
	Wno CHAR(9) NOT NULL,
	Bno CHAR(9) NOT NULL,
	Dno CHAR(10) NOT NULL,
	Ptel CHAR(11) NOT NULL,
	Pindate DATE NOT NULL,
	Poutdate DATE ,
	FOREIGN KEY(Wno,Bno) REFERENCES Bed(Wno,Bno),
	FOREIGN KEY(Dno) REFERENCES Doctor(Dno)
	);
CREATE TABLE drug_info (
    drug_id INT AUTO_INCREMENT COMMENT '药品编号（主键，自增）',
    drug_name VARCHAR(100) NOT NULL COMMENT '药品名称',
    specification VARCHAR(50) COMMENT '规格（如0.5g/片、10ml/支）',
    purchase_price DECIMAL(10, 2) NOT NULL COMMENT '进价（精确到分，例如8.50）',
    selling_price DECIMAL(10, 2) NOT NULL COMMENT '售价（精确到分，例如12.00）',
    is_prescription TINYINT(1) DEFAULT 0 COMMENT '是否为处方药（0=非处方药，1=处方药，默认非处方药）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（自动填充当前时间）',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间（数据修改时自动更新）',
COLUMN drug_category VARCHAR(50) COMMENT '药品类别';
    PRIMARY KEY (drug_id),
    INDEX idx_drug_name (drug_name)  -- 为药品名称添加索引，优化查询效率
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '药品信息表';

CREATE TABLE appointment_records (
    -- 预约号，自动递增，作为主键
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    -- 预约人姓名，不允许为空
    patient_name VARCHAR(50) NOT NULL,
    -- 年龄
    age INT,
    -- 性别，如 '男' 或 '女'
    gender VARCHAR(10),
    -- 症状描述
    symptom_description TEXT,
    -- 预约科室
    appointment_department VARCHAR(50),
    -- 联系电话
    contact_phone VARCHAR(20),
    -- 预约时间，默认值为当前时间
    appointment_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);    

-- 创建 Feedback 表
CREATE TABLE IF NOT EXISTS Feedback (
    -- Dno 字段，用于存储用户编号，这里假设为 VARCHAR 类型，长度为 20
    Dno VARCHAR(20) NOT NULL,
    -- FeedbackContent 字段，用于存储用户反馈内容，TEXT 类型可以存储较长的文本
    FeedbackContent TEXT NOT NULL,
    -- FeedbackTime 字段，用于存储反馈时间，TIMESTAMP 类型自动记录时间
    FeedbackTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- 为 Dno 字段添加索引，提高查询效率
    INDEX idx_Dno (Dno)
);    

CREATE TABLE patient_prescription (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '病人预约号，主键，自增',
    prescribed_drug_1 VARCHAR(100) COMMENT '开方药品1',
    prescribed_drug_2 VARCHAR(100) COMMENT '开方药品2',
    prescribed_drug_3 VARCHAR(100) COMMENT '开方药品3',
    total_cost DECIMAL(10, 2) COMMENT '总费用，精确到分'
);



