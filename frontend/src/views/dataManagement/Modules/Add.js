import React from "react";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import * as request from "superagent";

export class Add extends React.Component {
	render() {
		return (
			<div>
				<h1>Add Module Form</h1>
				<Form className="form" onSubmit={(e) => this.submit(e)}>
					<Form.Group as={Row} controlId="moduleId">
						<Form.Label column sm={2}>Module ID</Form.Label>
						<Col sm={10}>
							<Form.Control type="number"/>
						</Col>
					</Form.Group>
					<Form.Group as={Row} controlId="moduleCode">
						<Form.Label column sm={2}>Module Code</Form.Label>
						<Col sm={10}>
							<Form.Control type="text"/>
						</Col>
					</Form.Group>
					<Form.Group as={Row} controlId="moduleName">
						<Form.Label column sm={2}>Module Name</Form.Label>
						<Col sm={10}>
							<Form.Control type="text"/>
						</Col>
					</Form.Group>
					<Form.Group as={Row} controlId="moduleDescription">
						<Form.Label column sm={2}>Module Description</Form.Label>
						<Col sm={10}>
							<Form.Control as="textarea" rows="3"/>
						</Col>
					</Form.Group>
					<Form.Group as={Row} controlId="terms">
						<Form.Label column sm={2}>Select Default Terms</Form.Label>
						<Col sm={10}>
							<Form.Control as="select" multiple>
								<option>1</option>
								<option>2</option>
								<option>3</option>
							</Form.Control>
						</Col>
					</Form.Group>
					<Button type="submit" className="float-right">Add</Button>
				</Form>
			</div>
		);
	}
	
	submit(e) {
		e.preventDefault();
		let module = {
			"moduleId": e.target["moduleId"].value,
			"moduleCode": e.target["moduleCode"].value,
			"moduleName": e.target["moduleName"].value,
			"moduleDescription": e.target["moduleDescription"].value,
			"terms": e.target["terms"].value
		};
		request.put("/api/module/")
			.send(module).set("Accept", "application/json")
			.set("Access-Control-Allow-Origin", "*")
			.then(result =>
			alert("Added Module\n Response: \n" + JSON.stringify(result.body))
		)
			.catch(error =>
				alert("Added Module\n Response: \n" + JSON.stringify(error))
			);
	}
}