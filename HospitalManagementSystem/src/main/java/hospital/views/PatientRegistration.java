/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hospital.views;

import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author ITACHI
 */
public class PatientRegistration extends javax.swing.JInternalFrame {
private Integer patientID = null;
private Integer currentRoomID = null; // Track the current room for editing
    /**
     * Creates new form PatientRegistration
     * @param id
     */
    public PatientRegistration() {
        initComponents();
        setLocationRelativeTo(null);
        clearFields(); // Ensure fields are empty for new registration
        loadDepartments();
   }

    public PatientRegistration(int id) {
        initComponents();
        setLocationRelativeTo(null);
        this.patientID = id == 0? null : id;
        loadPatientDetails(id);
        loadAvailableRooms();
        loadDepartments();

    }

    
private void loadPatientDetails(int id) {
    if (id == 0){
        clearFields();
        return;
    }
    try {
        Connection con = hospital.db.ConnectDB.ConnectDB();
        String sql = "SELECT * FROM patients WHERE patient_id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            txtPName.setText(rs.getString("patient_name"));
            txtPFathersName.setText(rs.getString("fathers_name"));
            txtPAge.setText(String.valueOf(rs.getInt("age")));
            comboPGender.setSelectedItem(rs.getString("gender"));
            comboPBloodGroup.setSelectedItem(rs.getString("blood_group"));
            txtPContact.setText(rs.getString("contact_no"));
            txtPAddress.setText(rs.getString("address"));
            txtPRemarks.setText(rs.getString("remarks"));
            currentRoomID = rs.getInt("room_id"); // Store current room ID
        }else{
            JOptionPane.showMessageDialog(this, "No patient found with ID: " + id);
                clearFields();
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error loading patient details: " + e.getMessage());
            clearFields();
    }
}

private void loadDepartments() {
    cmbDepartment.removeAllItems();
    try {
        Connection con = hospital.db.ConnectDB.ConnectDB();
        String sql = "SELECT department_id, department_name FROM departments";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            cmbDepartment.addItem(rs.getInt("department_id") + " - " + rs.getString("department_name"));
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error loading departments: " + e.getMessage());
    }
}

private void loadDoctorsByDepartment(int departmentID) {
    cmbDoctor.removeAllItems();
    try {
        Connection con = hospital.db.ConnectDB.ConnectDB();
        String sql = "SELECT doctor_id, doctor_name FROM doctors WHERE department_id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, departmentID);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            cmbDoctor.addItem(rs.getInt("doctor_id") + " - " + rs.getString("doctor_name"));
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error loading doctors: " + e.getMessage());
    }
}   
    private void loadAvailableRooms() {
    cmbRoom.removeAllItems();
    try {
        Connection conn = hospital.db.ConnectDB.ConnectDB();
        //String sql = "SELECT room_id, room_no FROM room WHERE room_status = 'Available'";
        
        String sql = "SELECT room_no, room_type, bed_type, room_charges FROM room WHERE room_status = 'Available'";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        
        boolean hasAvailableRooms = false;
        while (rs.next()) {
           // int id = rs.getInt("room_id");
            hasAvailableRooms = true;
            String roomNo = rs.getString("room_no");
            String roomType = rs.getString("room_type");
            String bedType = rs.getString("bed_type");
            double roomCharges = rs.getDouble("room_charges");
            
            // Combines ID and name
            cmbRoom.addItem(roomNo + " - " + roomType + " - " + bedType + " - Ush" + roomCharges);
        }
          if (patientID != null && currentRoomID != null) {
                String currentRoomSql = "SELECT room_id, room_no, room_type, bed_type, room_charges FROM room WHERE room_id = ?";
                PreparedStatement currentRoomPst = conn.prepareStatement(currentRoomSql);
                currentRoomPst.setInt(1, currentRoomID);
                ResultSet currentRoomRs = currentRoomPst.executeQuery();
                if (currentRoomRs.next()) {
                    int id = currentRoomRs.getInt("room_id");
                    String roomNo = currentRoomRs.getString("room_no");
                    String roomType = currentRoomRs.getString("room_type");
                    String bedType = currentRoomRs.getString("bed_type");
                    double roomCharges = currentRoomRs.getDouble("room_charges");
                    cmbRoom.addItem(id + " - " + roomNo + " - " + roomType + " - " + bedType + " - $" + String.format("%.2f", roomCharges));
                    cmbRoom.setSelectedItem(id + " - " + roomNo + " - " + roomType + " - " + bedType + " - $" + String.format("%.2f", roomCharges));
                }
            }

            if (!hasAvailableRooms && (patientID == null || currentRoomID == null)) {
                cmbRoom.addItem("No available rooms");
            }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error loading rooms: " + e.getMessage());
    }
}

private void clearFields() {
        txtPName.setText("");
        txtPFathersName.setText("");
        txtPAge.setText("");
        comboPGender.setSelectedIndex(-1);
        comboPBloodGroup.setSelectedIndex(-1);
        txtPContact.setText("");
        txtPAddress.setText("");
        txtPRemarks.setText("");
        patientID = null;
        currentRoomID = null;
        loadAvailableRooms(); // Refresh room list
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtPName = new javax.swing.JTextField();
        txtPFathersName = new javax.swing.JTextField();
        txtPAddress = new javax.swing.JTextField();
        txtPContact = new javax.swing.JTextField();
        txtPEmail = new javax.swing.JTextField();
        txtPAge = new javax.swing.JTextField();
        comboPGender = new javax.swing.JComboBox<>();
        comboPBloodGroup = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPRemarks = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cmbDepartment = new javax.swing.JComboBox<>();
        cmbDoctor = new javax.swing.JComboBox<>();
        cmbRoom = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));

        jLabel3.setText("Patient Name");

        jLabel4.setText("Father's Name");

        jLabel5.setText("Address");

        jLabel6.setText("Contact No.");

        jLabel7.setText("Email-Id");

        jLabel8.setText("Age");

        jLabel9.setText("Gender");

        jLabel10.setText("Blood Group");

        jLabel11.setText("Remarks");

        txtPAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPAddressActionPerformed(evt);
            }
        });

        comboPGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female", "Other" }));

        comboPBloodGroup.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-" }));

        txtPRemarks.setColumns(20);
        txtPRemarks.setRows(5);
        jScrollPane1.setViewportView(txtPRemarks);

        jLabel2.setText("Department");

        jLabel12.setText("Doctor");

        jLabel13.setText("Room");

        cmbDepartment.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDepartmentItemStateChanged(evt);
            }
        });
        cmbDepartment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDepartmentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(84, 84, 84)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10)
                                .addComponent(jLabel9)
                                .addComponent(jLabel8)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(72, 72, 72)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtPFathersName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtPName, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtPAge, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(comboPGender, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(comboPBloodGroup, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPContact, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(66, 66, 66)
                        .addComponent(cmbDepartment, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(92, 92, 92)
                        .addComponent(cmbDoctor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(96, 96, 96)
                        .addComponent(cmbRoom, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPFathersName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtPContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtPEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtPAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(comboPGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(comboPBloodGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(cmbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(112, 112, 112))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("PATIENT DETAILS");

        jPanel3.setBackground(new java.awt.Color(51, 153, 255));

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnClear)
                    .addComponent(btnDelete)
                    .addComponent(btnSave)
                    .addComponent(btnUpdate))
                .addContainerGap(342, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(btnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDelete)
                .addGap(18, 18, 18)
                .addComponent(btnClear)
                .addContainerGap(147, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(332, 332, 332)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
    Connection conn = null;
    PreparedStatement pst = null;
   /* if(patientID != null){
        JOptionPane.showMessageDialog(this, "This patient already exists. Use Update instead." );
        return;
    }*/
    int roomID = Integer.parseInt(cmbRoom.getSelectedItem().toString().split(" - ")[0]);
    int doctorID = Integer.parseInt(cmbDoctor.getSelectedItem().toString().split(" - ")[0]);
    int departmentID = Integer.parseInt(cmbDepartment.getSelectedItem().toString().split(" - ")[0]);

    try {
        conn = hospital.db.ConnectDB.ConnectDB();
        String sql = "INSERT INTO patients (patient_name, fathers_name, age, gender, blood_group, contact_no, address, remarks, room_id, doctor_id, department_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        pst = conn.prepareStatement(sql);
        
        pst.setString(1, txtPName.getText());
        pst.setString(2, txtPFathersName.getText());
        pst.setInt(3, Integer.parseInt(txtPAge.getText()));
        pst.setString(4, comboPGender.getSelectedItem().toString());
        pst.setString(5, comboPBloodGroup.getSelectedItem().toString());
        pst.setString(6, txtPContact.getText());
        pst.setString(7, txtPAddress.getText());
        pst.setString(8, txtPRemarks.getText());
        pst.setInt(9, roomID);
        pst.setInt(10, doctorID);
        pst.setInt(11, departmentID);

        pst.executeUpdate();
        
        // NOW UPDATE THE ROOM STATUS TO 'Occupied'
        String updateRoomSql = "UPDATE room SET room_status = 'Occupied' WHERE room_id = ?";
        PreparedStatement pst2 = conn.prepareStatement(updateRoomSql);
        pst2.setInt(1, roomID);
        pst2.executeUpdate();
        
        JOptionPane.showMessageDialog(this, "Patient record saved successfully");
        clearFields();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e);
    }


        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
     clearFields();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
    if (patientID == null) {
        JOptionPane.showMessageDialog(this, "No patient selected to delete.");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this patient?", "Confirm", JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) return;

    try {
        Connection con = hospital.db.ConnectDB.ConnectDB();
        String sql = "DELETE FROM patients WHERE patient_id=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, patientID);
        pst.executeUpdate();

        JOptionPane.showMessageDialog(this, "Patient deleted.");
        clearFields();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e);
    }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed

    if (patientID == null) {
        JOptionPane.showMessageDialog(this, "No patient selected to update.");
        return;
    }

    try {
        Connection con = hospital.db.ConnectDB.ConnectDB();
        String sql = "UPDATE patients SET patient_name=?, fathers_name=?, age=?, gender=?, blood_group=?, contact_no=?, address=?, remarks=? WHERE patient_id=? ";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, txtPName.getText());
        pst.setString(2, txtPFathersName.getText());
        pst.setInt(3, Integer.parseInt(txtPAge.getText()));
        pst.setString(4, comboPGender.getSelectedItem().toString());
        pst.setString(5, comboPBloodGroup.getSelectedItem().toString());
        pst.setString(6, txtPContact.getText());
        pst.setString(7, txtPAddress.getText());
        pst.setString(8, txtPRemarks.getText());
        pst.setInt(9, patientID);

        pst.executeUpdate();
        JOptionPane.showMessageDialog(this, "Patient record updated.");
        clearFields();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e);
    }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void cmbDepartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDepartmentActionPerformed
     /*   
    if (evt.getStateChange() == ItemEvent.SELECTED) {
        String selected = (String) cmbDepartment.getSelectedItem();
        int deptID = Integer.parseInt(selected.split(" - ")[0]);
        loadDoctorsByDepartment(deptID);
    }*/
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbDepartmentActionPerformed

    private void cmbDepartmentItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDepartmentItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
        String selected = (String) cmbDepartment.getSelectedItem();
            if (selected != null) {
                int deptID = Integer.parseInt(selected.split(" - ")[0]);
                loadDoctorsByDepartment(deptID);
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbDepartmentItemStateChanged

    private void txtPAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPAddressActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PatientRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PatientRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PatientRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PatientRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PatientRegistration(0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbDepartment;
    private javax.swing.JComboBox<String> cmbDoctor;
    private javax.swing.JComboBox<String> cmbRoom;
    private javax.swing.JComboBox<String> comboPBloodGroup;
    private javax.swing.JComboBox<String> comboPGender;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtPAddress;
    private javax.swing.JTextField txtPAge;
    private javax.swing.JTextField txtPContact;
    private javax.swing.JTextField txtPEmail;
    private javax.swing.JTextField txtPFathersName;
    private javax.swing.JTextField txtPName;
    private javax.swing.JTextArea txtPRemarks;
    // End of variables declaration//GEN-END:variables

    private void setLocationRelativeTo(Object object) {
    if (getParent() != null) {
        // Center the JInternalFrame within its parent (e.g., JDesktopPane)
        int parentWidth = getParent().getWidth();
        int parentHeight = getParent().getHeight();
        int frameWidth = getWidth();
        int frameHeight = getHeight();
        setLocation((parentWidth - frameWidth) / 2, (parentHeight - frameHeight) / 2);
    } else {
        // Fallback: Position at a default location if no parent
        setLocation(0, 0);
    }
}
}
