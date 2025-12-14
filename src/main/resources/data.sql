-- locations
INSERT INTO locations (id, name) VALUES
(1, '総務部'),
(2, '開発部'),
(3, '会議室')
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- members（bcryptハッシュは画像の値そのまま）
INSERT INTO members (id, name, login_id, login_pass) VALUES
(1, '山田太郎', 'taro', '$2a$10$WZHo5Z1FNVKo5rZcDdJ/seUemb1YC.T.z3AespUAwqB7P9/NGEUH.'),
(2, '鈴木花子', 'hana', '$2a$10$vgBi0gjpGqLKq0QRetnJ.w/LDSqg3zITXJzTTIa2c0Y/dmk.IZfJm')
ON DUPLICATE KEY UPDATE
  name = VALUES(name),
  login_id = VALUES(login_id),
  login_pass = VALUES(login_pass);

-- items（registered/updated も画像の値そのまま）
INSERT INTO items (id, name, amount, location_id, note, registered, updated) VALUES
(1, 'デスクトップPC', 1, 1, NULL, '2024-01-15 00:00:00', '2024-01-15 00:00:00'),
(2, 'デスクトップPC', 7, 2, NULL, '2024-02-10 00:00:00', '2024-02-10 00:00:00'),
(3, 'ノートPC',      2, 1, 'Webカメラ付き', '2024-02-15 00:00:00', '2024-02-15 00:00:00'),
(4, 'ノートPC',      3, 2, NULL, '2024-03-05 00:00:00', '2024-03-05 00:00:00'),
(5, '無線ルータ',     1, 2, NULL, '2024-03-10 00:00:00', '2024-03-10 00:00:00'),
(6, 'プロジェクター', 1, 3, NULL, '2024-03-15 00:00:00', '2024-03-15 00:00:00')
ON DUPLICATE KEY UPDATE
  name = VALUES(name),
  amount = VALUES(amount),
  location_id = VALUES(location_id),
  note = VALUES(note),
  registered = VALUES(registered),
  updated = VALUES(updated);
