
# 🛠️ Terraform - Modify AWS Infrastructure

This README walks you through the process of modifying existing AWS infrastructure using Terraform, following the HashiCorp [Change Infrastructure](https://developer.hashicorp.com/terraform/tutorials/aws-get-started/aws-change) tutorial.

---

## 📦 Prerequisites

- AWS CLI configured with credentials (`aws configure`)
- Terraform installed ([Install Terraform](https://developer.hashicorp.com/terraform/downloads))
- An existing Terraform configuration (VPC or EC2 example)
- A basic understanding of AWS and Terraform

---

## 🧾 Step-by-Step Guide

### 1. 🔄 Modify Existing Terraform Configuration

For example, let's say your `main.tf` contains an EC2 instance with:

```hcl
resource "aws_instance" "example" {
  ami           = "ami-0c55b159cbfafe1f0"
  instance_type = "t2.micro"
}
```

To modify the instance type from `t2.micro` to `t2.small`, simply change:

```hcl
instance_type = "t2.small"
```

### 2. 📋 Review the Execution Plan

Run the following to see what changes Terraform will make:

```bash
terraform plan
```

The output will show the modification with a `~` indicating the change:

```
~ instance_type: "t2.micro" => "t2.small"
```

### 3. 🚀 Apply the Change

Apply the updated configuration:

```bash
terraform apply
```

Type `yes` when prompted to approve the changes.

---

## 🧹 Clean Up (Optional)

To destroy the created infrastructure:

```bash
terraform destroy
```

---

## 📁 File Structure Example

```plaintext
terraform-aws-change/
├── main.tf
├── variables.tf
└── README.md
```

---

## 🧠 Tips

- Always run `terraform plan` before `apply` to avoid unintended changes.
- Use version control (e.g., Git) to track changes to your Terraform code.
- Back up your `.tfstate` files or use a remote backend (like S3) for team collaboration.

---

## 🔗 Useful Commands

```bash
# Show what Terraform will do
terraform plan

# Apply the planned changes
terraform apply

# Destroy the infrastructure
terraform destroy

# Format your code
terraform fmt

# Validate configuration
terraform validate
```
