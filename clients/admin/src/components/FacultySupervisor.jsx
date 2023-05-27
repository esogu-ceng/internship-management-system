import React, { useState, useEffect } from "react";
import {
  Button,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  IconButton,
  Typography,
  makeStyles,
  useMediaQuery,
  useTheme,
} from "@material-ui/core";
import {
  Delete,
  Edit,
  Add,
  Close,
  Check,
  SupervisorAccount,
} from "@material-ui/icons";

const API_URL = "api/facultySupervisor/"; // Replace with your API endpoint

const useStyles = makeStyles((theme) => ({
  addButton: {
    marginBottom: theme.spacing(2),
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    marginLeft: 15,
  },
  updateButton: {
    marginRight: theme.spacing(1),
  },
  deleteButton: {
    color: theme.palette.error.main,
  },
  title: {
    display: "flex",
    alignItems: "center",
    fontFamily: "Roboto, Helvetica, sans-serif",
  },
  titleIcon: {
    marginLeft: theme.spacing(1),
  },
  tableHeaderIcon: {
    marginRight: theme.spacing(1),
  },
  popupHeader: {
    display: "flex",
    alignItems: "center",
  },
  popupHeaderIcon: {
    marginRight: theme.spacing(1),
  },
}));

function FacultySupervisor() {
  const classes = useStyles();
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down("sm"));

  const [facultySupervisors, setFacultySupervisors] = useState([]);
  const [openAddDialog, setOpenAddDialog] = useState(false);
  const [openUpdateDialog, setOpenUpdateDialog] = useState(false);
  const [openDeleteDialog, setOpenDeleteDialog] = useState(false);
  const [selectedFacultySupervisor, setSelectedFacultySupervisor] =
    useState(null);
  const [newFacultySupervisor, setNewFacultySupervisor] = useState({
    id: "",
    name: "",
    surname: "",
    phoneNumber: "",
    supervisorNo: "",
    createDate: "",
    updateDate: "",
    userId: "",
    facultyId: "",
  });

  useEffect(() => {
    fetchFacultySupervisors();
  }, []);

  const fetchFacultySupervisors = async () => {
    try {
      const response = await fetch(API_URL);
      const data = await response.json();
      setFacultySupervisors(data);
    } catch (error) {
      console.error("Error fetching faculty supervisors:", error);
    }
  };

  const handleAddFacultySupervisor = async () => {
    try {
      const response = await fetch(API_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization:
            "Basic " + btoa("ykartal@ogu.edu.tr:sdfasdfadfasdfasdfasdf"),
        },
        body: JSON.stringify(newFacultySupervisor),
      });
      if (response.ok) {
        fetchFacultySupervisors();
        setOpenAddDialog(false);
        setNewFacultySupervisor({
          id: "",
          name: "",
          surname: "",
          phoneNumber: "",
          supervisorNo: "",
          createDate: "",
          updateDate: "",
          userId: "",
          facultyId: "",
        });
      } else {
        console.error("Error adding faculty supervisor:", response.statusText);
      }
    } catch (error) {
      console.error("Error adding faculty supervisor:", error);
    }
  };

  const handleDeleteFacultySupervisor = async (id) => {
    try {
      const response = await fetch(`${API_URL}/${id}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization:
            "Basic " + btoa("ykartal@ogu.edu.tr:sdfasdfadfasdfasdfasdf"),
        },
      });
      if (response.ok) {
        fetchFacultySupervisors();
        setOpenDeleteDialog(false);
      } else {
        console.error(
          "Error deleting faculty supervisor:",
          response.statusText
        );
      }
    } catch (error) {
      console.error("Error deleting faculty supervisor:", error);
    }
  };

  const handleUpdateFacultySupervisor = async (
    id,
    updatedFacultySupervisor
  ) => {
    try {
      const response = await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization:
            "Basic " + btoa("ykartal@ogu.edu.tr:sdfasdfadfasdfasdfasdf"),
        },
        body: JSON.stringify(updatedFacultySupervisor),
      });
      if (response.ok) {
        fetchFacultySupervisors();
        setOpenUpdateDialog(false);
        setSelectedFacultySupervisor(null);
      } else {
        console.error(
          "Error updating faculty supervisor:",
          response.statusText
        );
      }
    } catch (error) {
      console.error("Error updating faculty supervisor:", error);
    }
  };

  const handleEditButtonClick = (facultySupervisor) => {
    setSelectedFacultySupervisor(facultySupervisor);
    setOpenUpdateDialog(true);
  };

  const handleDeleteButtonClick = (facultySupervisor) => {
    setSelectedFacultySupervisor(facultySupervisor);
    setOpenDeleteDialog(true);
  };

  const handleAddDialogClose = () => {
    setOpenAddDialog(false);
    setNewFacultySupervisor({
      id: "",
      name: "",
      surname: "",
      phoneNumber: "",
      supervisorNo: "",
      createDate: "",
      updateDate: "",
      userId: "",
      facultyId: "",
    });
  };

  const handleUpdateDialogClose = () => {
    setOpenUpdateDialog(false);
    setSelectedFacultySupervisor(null);
  };

  const handleDeleteDialogClose = () => {
    setOpenDeleteDialog(false);
    setSelectedFacultySupervisor(null);
  };

  return (
    <div className="App">
      <Typography
        style={{ marginLeft: 15 }}
        variant="h4"
        align="center"
        gutterBottom
        className={classes.title}>
        Fakülte Sorumlusu
        <SupervisorAccount className={classes.titleIcon} />
      </Typography>

      <Button
        className={classes.addButton}
        startIcon={<Add />}
        variant="contained"
        color="primary"
        onClick={() => setOpenAddDialog(true)}
        fullWidth={isMobile}>
        Fakülte Sorumlusu Ekle
      </Button>

      <TableContainer
        style={{
          margin: 10,
          paddingRight: 10,
          fontFamily: "Droid Sans",
          fontWeight: "bold",
        }}
        component={Paper}>
        <Table style={{ fontWeight: "bold" }}>
          <TableHead>
            <TableRow>
              <TableCell>Id</TableCell>
              <TableCell>Adı</TableCell>
              <TableCell>Soyadı</TableCell>
              <TableCell>Telefon Numarası</TableCell>
              <TableCell>Fakülte Sorumlusu Numarası</TableCell>
              <TableCell>Eylem</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {facultySupervisors.map((facultySupervisor, index) => (
              <TableRow
                key={facultySupervisor.id}
                style={{
                  backgroundColor: index % 2 === 0 ? "white" : "lightblue",
                }}>
                <TableCell>{facultySupervisor.id}</TableCell>
                <TableCell>{facultySupervisor.name}</TableCell>
                <TableCell>{facultySupervisor.surname}</TableCell>
                <TableCell>{facultySupervisor.phoneNumber}</TableCell>
                <TableCell>{facultySupervisor.supervisorNo}</TableCell>
                <TableCell>
                  <IconButton
                    className={classes.updateButton}
                    onClick={() => handleEditButtonClick(facultySupervisor)}>
                    <Edit />
                  </IconButton>
                  <IconButton
                    className={classes.deleteButton}
                    onClick={() => handleDeleteButtonClick(facultySupervisor)}>
                    <Delete />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <Dialog open={openAddDialog} onClose={handleAddDialogClose}>
        <DialogTitle className={classes.popupHeader}>
          <Add className={classes.popupHeaderIcon} />
          Add Faculty Supervisor
        </DialogTitle>
        <DialogContent>
          <TextField
            label="Adı"
            value={newFacultySupervisor.name}
            onChange={(e) =>
              setNewFacultySupervisor({
                ...newFacultySupervisor,
                name: e.target.value,
              })
            }
            fullWidth
          />
          <TextField
            label="Soyadı"
            value={newFacultySupervisor.surname}
            onChange={(e) =>
              setNewFacultySupervisor({
                ...newFacultySupervisor,
                surname: e.target.value,
              })
            }
            fullWidth
          />
          <TextField
            label="Telefon Numarası"
            value={newFacultySupervisor.phoneNumber}
            onChange={(e) =>
              setNewFacultySupervisor({
                ...newFacultySupervisor,
                phoneNumber: e.target.value,
              })
            }
            fullWidth
          />
          <TextField
            label="Fakülte Sorumlusu Numarası"
            value={newFacultySupervisor.supervisorNo}
            onChange={(e) =>
              setNewFacultySupervisor({
                ...newFacultySupervisor,
                supervisorNo: e.target.value,
              })
            }
            fullWidth
          />
        </DialogContent>
        <DialogActions>
          <Button
            startIcon={<Close />}
            onClick={handleAddDialogClose}
            color="secondary">
            İptal
          </Button>
          <Button
            startIcon={<Check />}
            onClick={handleAddFacultySupervisor}
            color="primary">
            Ekle
          </Button>
        </DialogActions>
      </Dialog>

      <Dialog open={openUpdateDialog} onClose={handleUpdateDialogClose}>
        <DialogTitle className={classes.popupHeader}>
          <Edit className={classes.popupHeaderIcon} />
          Fakültes Sorumlusu Güncelle
        </DialogTitle>
        <DialogContent>
          {selectedFacultySupervisor && (
            <>
              <TextField
                label="ID"
                value={selectedFacultySupervisor.id}
                disabled
                fullWidth
              />
              <TextField
                label="Adı"
                value={selectedFacultySupervisor.name}
                onChange={(e) =>
                  setSelectedFacultySupervisor({
                    ...selectedFacultySupervisor,
                    name: e.target.value,
                  })
                }
                fullWidth
              />
              <TextField
                label="Soyadı"
                value={selectedFacultySupervisor.surname}
                onChange={(e) =>
                  setSelectedFacultySupervisor({
                    ...selectedFacultySupervisor,
                    surname: e.target.value,
                  })
                }
                fullWidth
              />
              <TextField
                label="Telefon Numarası"
                value={selectedFacultySupervisor.phoneNumber}
                onChange={(e) =>
                  setSelectedFacultySupervisor({
                    ...selectedFacultySupervisor,
                    phoneNumber: e.target.value,
                  })
                }
                fullWidth
              />
              <TextField
                label="Fakülte Sorumlusu Numarası"
                value={selectedFacultySupervisor.supervisorNo}
                onChange={(e) =>
                  setSelectedFacultySupervisor({
                    ...selectedFacultySupervisor,
                    supervisorNo: e.target.value,
                  })
                }
                fullWidth
              />
            </>
          )}
        </DialogContent>
        <DialogActions>
          <Button
            startIcon={<Close />}
            onClick={handleUpdateDialogClose}
            color="secondary">
            İptal
          </Button>
          <Button
            startIcon={<Check />}
            onClick={() =>
              handleUpdateFacultySupervisor(selectedFacultySupervisor.id, {
                name: selectedFacultySupervisor.name,
                surname: selectedFacultySupervisor.surname,
                phoneNumber: selectedFacultySupervisor.phoneNumber,
                supervisorNo: selectedFacultySupervisor.supervisorNo,
                createDate: selectedFacultySupervisor.createDate,
                updateDate: selectedFacultySupervisor.updateDate,
                userId: selectedFacultySupervisor.userId,
                facultyId: selectedFacultySupervisor.facultyId,
              })
            }
            color="primary">
            Güncelle
          </Button>
        </DialogActions>
      </Dialog>

      <Dialog open={openDeleteDialog} onClose={handleDeleteDialogClose}>
        <DialogTitle className={classes.popupHeader}>
          <Delete className={classes.popupHeaderIcon} />
          Fakültes Sorumlusu Sil
        </DialogTitle>
        <DialogContent>
          {selectedFacultySupervisor && (
            <>
              <Typography variant="h6" gutterBottom>
                Id:
              </Typography>
              <Typography variant="body1" gutterBottom>
                {selectedFacultySupervisor.id}
              </Typography>
              <Typography variant="h6" gutterBottom>
                Adı:
              </Typography>
              <Typography variant="body1" gutterBottom>
                {selectedFacultySupervisor.name}
              </Typography>
              <Typography variant="h6" gutterBottom>
                Soyadı:
              </Typography>
              <Typography variant="body1" gutterBottom>
                {selectedFacultySupervisor.surname}
              </Typography>
              <Typography variant="h6" gutterBottom>
                Telefon Numarsı:
              </Typography>
              <Typography variant="body1" gutterBottom>
                {selectedFacultySupervisor.phoneNumber}
              </Typography>
              <Typography variant="h6" gutterBottom>
                Fakültes Sorumlusu Numarası:
              </Typography>
              <Typography variant="body1" gutterBottom>
                {selectedFacultySupervisor.supervisorNo}
              </Typography>
            </>
          )}
        </DialogContent>
        <DialogActions>
          <Button
            startIcon={<Close />}
            onClick={handleDeleteDialogClose}
            color="primary">
            İptal
          </Button>
          <Button
            startIcon={<Delete />}
            onClick={() =>
              handleDeleteFacultySupervisor(selectedFacultySupervisor.id)
            }
            color="secondary">
            Sil
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}

export default FacultySupervisor;
