Expand the name of the chart.

# Set the current date
{{- define "mychart.labels.date" -}}
  {{- now }}
{{- end }}

# Set the version label
{{- define "mychart.labels.version" -}}
  1.0.0
{{- end }}

{{- define "resource-service-chart.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}


Create chart name and version as used by the chart label.

{{- define "first-chart.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}


Common labels

{{- define "first-chart.labels" -}}
helm.sh/chart: {{ include "first-chart.chart" . }}
{{ include "first-chart.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}


Selector labels

{{- define "first-chart.selectorLabels" -}}
app.kubernetes.io/name: {{ include "first-chart.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}


Create the name of the service account to use

{{- define "first-chart.serviceAccountName" -}}
{{- if .Values.serviceAccount.create }}
{{- default (include "first-chart.fullname" .) .Values.serviceAccount.name }}
{{- else }}
{{- default "default" .Values.serviceAccount.name }}
{{- end }}
{{- end }}
